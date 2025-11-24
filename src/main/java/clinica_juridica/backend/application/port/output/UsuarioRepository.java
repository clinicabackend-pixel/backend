package clinica_juridica.backend.application.port.output;

import clinica_juridica.backend.domain.entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(String id);
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findAll();
    void deleteById(String id);
}

