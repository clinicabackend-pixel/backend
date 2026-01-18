package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.request.UsuarioRequest;
import clinica_juridica.backend.models.Coordinador;
import clinica_juridica.backend.models.Estudiante;
import clinica_juridica.backend.models.Profesor;
import clinica_juridica.backend.models.Usuario;
import clinica_juridica.backend.repository.CoordinadorRepository;
import clinica_juridica.backend.repository.EstudianteRepository;
import clinica_juridica.backend.repository.ProfesorRepository;
import clinica_juridica.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final ProfesorRepository profesorRepository;
    private final EstudianteRepository estudianteRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
            CoordinadorRepository coordinadorRepository,
            ProfesorRepository profesorRepository,
            EstudianteRepository estudianteRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.coordinadorRepository = coordinadorRepository;
        this.profesorRepository = profesorRepository;
        this.estudianteRepository = estudianteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<Usuario> findAllUsuarios(String estatus) {
        if (estatus != null && !estatus.isEmpty()) {
            return usuarioRepository.findByStatus(estatus);
        }
        return usuarioRepository.findAll();
    }

    public Usuario findUsuarioByUsername(String username) {
        Objects.requireNonNull(username, "Username cannot be null");
        return usuarioRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Autowired
    private clinica_juridica.backend.security.JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void createUsuario(UsuarioRequest request) {
        String username = Objects.requireNonNull(request.getUsername(), "Username cannot be null");
        if (usuarioRepository.existsById(username)) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        if (request.getCedula() != null
                && usuarioRepository.findAll().stream()
                        .anyMatch(u -> Objects.equals(request.getCedula(), u.getCedula()))) {
            throw new RuntimeException("La cédula ya está registrada");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setCedula(request.getCedula());
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTipo(request.getTipoUsuario());
        usuario.setStatus("ACTIVO");

        // Generate random internal password
        String randomPassword = java.util.UUID.randomUUID().toString();
        String hashedPassword = passwordEncoder.encode(randomPassword);
        usuario.setContrasena(hashedPassword);
        usuario.setNew(true);

        usuarioRepository.save(usuario);

        // Save role specific entities
        switch (request.getTipoUsuario()) {
            case "COORDINADOR":
                Coordinador coordinador = new Coordinador(request.getUsername());
                coordinadorRepository.save(coordinador);
                break;
            case "PROFESOR":
                Profesor profesor = new Profesor(request.getUsername(), request.getTermino());
                profesorRepository.save(profesor);
                break;
            case "ESTUDIANTE":
                Estudiante estudiante = new Estudiante(request.getUsername(), request.getTermino(),
                        request.getTipoDeEstudiante(), request.getNrc());
                estudianteRepository.save(estudiante);
                break;
            default:
                throw new RuntimeException("Tipo de usuario no válido");
        }

        // Generate invitation token and send email
        // Note: We use the HASHED password as part of the key.
        String invitationToken = jwtUtil.generateInvitationToken(username, hashedPassword);
        emailService.sendInvitationEmail(usuario.getEmail(), invitationToken);
    }

    public void setupPassword(String token, String nuevaContrasena) {
        String username = jwtUtil.extractUsername(token);
        Usuario usuario = findUsuarioByUsername(username);

        // Validate token using current stored password hash
        if (!jwtUtil.validateInvitationToken(token, username, usuario.getContrasena())) {
            throw new RuntimeException("Token inválido o expirado. Es posible que ya hayas configurado tu contraseña.");
        }

        // Disable token reused by changing the password (which changes the hash)
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void updateUsuario(String username, UsuarioRequest request) {
        Usuario usuario = findUsuarioByUsername(username);

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        // Only update password if provided
        if (request.getContrasena() != null && !request.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        }
        // Status update
        if (request.getEstatus() != null) {
            usuario.setStatus(request.getEstatus());
        }

        usuarioRepository.save(usuario);
    }

    public void deleteUsuario(String username) {
        Usuario usuario = findUsuarioByUsername(username);
        usuario.setStatus("INACTIVO");
        usuarioRepository.save(usuario);
    }

}
