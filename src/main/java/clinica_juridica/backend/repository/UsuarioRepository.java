package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Usuario;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    @Override
    @NonNull
    @Query("SELECT * FROM usuarios")
    List<Usuario> findAll();

    Page<Usuario> findAll(Pageable pageable);

    @Override
    @NonNull
    @Query("SELECT * FROM usuarios WHERE username = :username")
    Optional<Usuario> findById(@NonNull String username);

    @Query("SELECT * FROM usuarios WHERE username = :username")
    Optional<Usuario> findByUsername(String username);

    @Query("SELECT * FROM usuarios WHERE status = :status")
    List<Usuario> findByStatus(String status);

    Page<Usuario> findByStatus(String status, Pageable pageable);

    @Query("SELECT * FROM usuarios WHERE email = :email")
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT * FROM usuarios WHERE cedula = :cedula")
    Optional<Usuario> findByCedula(String cedula);
}
