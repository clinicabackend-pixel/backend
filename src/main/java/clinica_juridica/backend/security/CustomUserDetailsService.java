package clinica_juridica.backend.security;

import clinica_juridica.backend.models.Usuario;
import clinica_juridica.backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convert the String 'tipo' to a Spring Security Authority
        // Assuming 'tipo' stored in DB matches "ESTUDIANTE", "PROFESOR", "COORDINADOR"
        String roleName = "ROLE_" + usuario.getTipo().toUpperCase();

        boolean isEnabled = "ACTIVO".equalsIgnoreCase(usuario.getStatus());

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getContrasena())
                .disabled(!isEnabled) // Spring Security checks this
                .authorities(roleName)
                .build();
    }
}
