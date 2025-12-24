package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Usuario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("null")
public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByUsername(String username);
}
