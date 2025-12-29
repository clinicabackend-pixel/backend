package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.request.UsuarioRequest;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    public Iterable<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Iterable<Usuario> findAllActiveUsuarios() {
        return usuarioRepository.findByStatus("ACTIVO");
    }

    public Usuario findUsuarioByUsername(String username) {
        Objects.requireNonNull(username, "Username cannot be null");
        return usuarioRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public void createUsuario(UsuarioRequest request) {
        String username = Objects.requireNonNull(request.getUsername(), "Username cannot be null");
        if (usuarioRepository.existsById(username)) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        if (request.getCedula() != null
                && usuarioRepository.findAll().stream()
                        .anyMatch(u -> Objects.equals(request.getCedula(), u.getCedula()))) {
            // Note: Ideally request.getCedula() check should be a DB query, but findAll
            // stream is ok for now given repository limitations
            throw new RuntimeException("La cédula ya está registrada");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setCedula(request.getCedula());
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setTipo(request.getTipoUsuario());
        usuario.setStatus("ACTIVO");

        usuarioRepository.save(usuario);

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

        // Update specific roles fields if necessary (basic implementation updates
        // common user fields)
        // For full update of role specific fields (like NRC), specific logic would be
        // needed here.
    }

    public void deleteUsuario(String username) {
        Usuario usuario = findUsuarioByUsername(username);
        usuario.setStatus("INACTIVO");
        usuarioRepository.save(usuario);
    }

}
