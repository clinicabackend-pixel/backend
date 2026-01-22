package clinica_juridica.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import clinica_juridica.backend.dto.request.UsuarioRequest;
import clinica_juridica.backend.models.Coordinador;
import clinica_juridica.backend.models.Estudiante;
import clinica_juridica.backend.models.Profesor;
import clinica_juridica.backend.models.Usuario;
import clinica_juridica.backend.repository.CoordinadorRepository;
import clinica_juridica.backend.repository.EstudianteRepository;
import clinica_juridica.backend.repository.ProfesorRepository;
import clinica_juridica.backend.repository.UsuarioRepository;
import clinica_juridica.backend.repository.SemestreRepository; // Added import
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@SuppressWarnings("null")
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final ProfesorRepository profesorRepository;
    private final EstudianteRepository estudianteRepository;
    private final SemestreRepository semestreRepository;
    private final org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
            CoordinadorRepository coordinadorRepository,
            ProfesorRepository profesorRepository,
            EstudianteRepository estudianteRepository,
            SemestreRepository semestreRepository,
            org.springframework.jdbc.core.JdbcTemplate jdbcTemplate,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.coordinadorRepository = coordinadorRepository;
        this.profesorRepository = profesorRepository;
        this.estudianteRepository = estudianteRepository;
        this.semestreRepository = semestreRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<Usuario> findAllUsuarios(String estatus) {
        if (estatus != null && !estatus.isEmpty()) {
            return usuarioRepository.findByStatus(estatus);
        }
        return usuarioRepository.findAll();
    }

    public Page<Usuario> findAllUsuarios(String estatus, Pageable pageable) {
        if (estatus != null && !estatus.isEmpty()) {
            return usuarioRepository.findByStatus(estatus, pageable);
        }
        return usuarioRepository.findAll(pageable);
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
        usuario.setTipo(request.getTipoUsuario());

        // Normalize Sexo (Database requires Title Case)
        String sexo = request.getSexo();
        if (sexo != null) {
            if (sexo.equalsIgnoreCase("MASCULINO"))
                sexo = "Masculino";
            else if (sexo.equalsIgnoreCase("FEMENINO"))
                sexo = "Femenino";
        }
        usuario.setSexo(sexo);
        usuario.setStatus("ACTIVO");

        // Password handling
        if (request.getContrasena() != null && !request.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        } else {
            String randomPassword = java.util.UUID.randomUUID().toString();
            usuario.setContrasena(passwordEncoder.encode(randomPassword));
        }
        usuario.setNew(true);

        usuarioRepository.save(usuario);

        // Save role specific entities
        switch (request.getTipoUsuario()) {
            case "COORDINADOR":
                Coordinador coordinador = new Coordinador(request.getUsername());
                coordinador.setNew(true);
                coordinadorRepository.save(coordinador);
                break;
            case "PROFESOR":
                String terminoProf = request.getTermino();
                if (terminoProf == null || terminoProf.isEmpty()) {
                    var active = semestreRepository.findActiveSemester();
                    terminoProf = (active != null) ? active.getTermino() : null;
                }
                Profesor profesor = new Profesor(request.getUsername(), terminoProf);
                profesor.setNew(true);
                profesorRepository.save(profesor);
                break;
            case "ESTUDIANTE":
                String terminoEst = request.getTermino();
                if (terminoEst == null || terminoEst.isEmpty()) {
                    var active = semestreRepository.findActiveSemester();
                    terminoEst = (active != null) ? active.getTermino() : null;
                }
                Estudiante estudiante = new Estudiante(request.getUsername(), terminoEst,
                        request.getTipoDeEstudiante(), request.getNrc());
                estudiante.setNew(true);
                estudianteRepository.save(estudiante);
                break;
            default:
                throw new RuntimeException("Tipo de usuario no válido");
        }

        // Generate invitation token and send email
        // Note: We use the HASHED password as part of the key.
        try {
            String invitationToken = jwtUtil.generateInvitationToken(username, usuario.getContrasena());
            emailService.sendInvitationEmail(usuario.getEmail(), invitationToken);
        } catch (Exception e) {
            // Log error but do not fail user creation
            System.err.println("Error enviando correo de invitación a " + usuario.getEmail() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setupPassword(String token, String nuevaContrasena) {
        String username = jwtUtil.extractUsernameFromInvitation(token);
        if (username == null) {
            throw new RuntimeException("Token inválido (imposible leer usuario)");
        }
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
        if (request.getSexo() != null) {
            String sexo = request.getSexo();
            if (sexo.equalsIgnoreCase("MASCULINO"))
                sexo = "Masculino";
            else if (sexo.equalsIgnoreCase("FEMENINO"))
                sexo = "Femenino";
            usuario.setSexo(sexo);
        }

        usuarioRepository.save(usuario);

        // Update term for specific roles
        if (request.getTermino() != null && !request.getTermino().isEmpty()) {
            if ("PROFESOR".equals(usuario.getTipo())) {
                profesorRepository.findById(username).ifPresent(p -> {
                    p.setTermino(request.getTermino());
                    profesorRepository.save(p);
                });
            } else if ("ESTUDIANTE".equals(usuario.getTipo())) {
                estudianteRepository.findById(username).ifPresent(e -> {
                    e.setTermino(request.getTermino());
                    estudianteRepository.save(e);
                });
            }
        }
    }

    public void deleteUsuario(String username) {
        Usuario usuario = findUsuarioByUsername(username);
        usuario.setStatus("INACTIVO");
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteUsuarioPermanently(String username) {
        Usuario usuario = findUsuarioByUsername(username);

        // 1. Clean up dependencies using JDBC to avoid loading everything
        // Delete associations (Many-to-Many or Composite PKs containing username)
        jdbcTemplate.update("DELETE FROM casos_asignados WHERE username = ?", username);
        jdbcTemplate.update("DELETE FROM casos_supervisados WHERE username = ?", username);
        jdbcTemplate.update("DELETE FROM acciones_ejecutadas WHERE username = ?", username);
        jdbcTemplate.update("DELETE FROM encuentros_atendidos WHERE username = ?", username);

        // Nullify ownership/references (Foreign Keys where possible)
        jdbcTemplate.update("UPDATE casos SET username = NULL WHERE username = ?", username);
        jdbcTemplate.update("UPDATE accion SET username = NULL WHERE username = ?", username);
        jdbcTemplate.update("UPDATE encuentros SET username = NULL WHERE username = ?", username);
        jdbcTemplate.update("UPDATE documentos SET username = NULL WHERE username = ?", username);

        // 2. Delete from role specific tables (Using JDBC to ensure cleanup regardless
        // of entity state)
        jdbcTemplate.update("DELETE FROM estudiantes WHERE username = ?", username);
        jdbcTemplate.update("DELETE FROM profesores WHERE username = ?", username);
        jdbcTemplate.update("DELETE FROM coordinadores WHERE username = ?", username);

        // 3. Delete user
        usuarioRepository.delete(usuario);
    }

    public String getTerminoByUsername(String username, String tipo) {
        if ("ESTUDIANTE".equals(tipo)) {
            return estudianteRepository.findByUsername(username).map(Estudiante::getTermino).orElse(null);
        } else if ("PROFESOR".equals(tipo)) {
            return profesorRepository.findByUsername(username).map(Profesor::getTermino).orElse(null);
        }
        return null;
    }

}
