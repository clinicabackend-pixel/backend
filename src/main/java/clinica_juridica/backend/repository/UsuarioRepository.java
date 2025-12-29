package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Usuario;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    @Override
    @NonNull
    @Query("SELECT * FROM usuarios")
    List<Usuario> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM usuarios WHERE username = :username")
    Optional<Usuario> findById(@NonNull String username);

    @Query("SELECT * FROM usuarios WHERE username = :username")
    Optional<Usuario> findByUsername(String username);

    @Query("SELECT * FROM usuarios WHERE status = :status")
    List<Usuario> findByStatus(String status);
}
