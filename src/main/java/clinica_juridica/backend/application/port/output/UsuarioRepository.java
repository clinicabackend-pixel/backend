package clinica_juridica.backend.application.port.output;

import java.util.List;
import java.util.Optional;

import clinica_juridica.backend.domain.models.Usuario;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(String id);
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findAll();
    void deleteById(String id);
}

