package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Usuario;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    @Override
    @Query("SELECT * FROM usuarios")
    List<Usuario> findAll();

    @Override
    @Query("SELECT * FROM usuarios WHERE username = :username")
    Optional<Usuario> findById(String username);

    @Query("SELECT * FROM usuarios WHERE username = :username")
    Optional<Usuario> findByUsername(String username);
}
