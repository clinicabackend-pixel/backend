package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Profesor;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesorRepository extends CrudRepository<Profesor, String> {
    @Override
    @NonNull
    @Query("SELECT * FROM profesores")
    List<Profesor> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM profesores WHERE cedula = :cedula")
    Optional<Profesor> findById(@NonNull String cedula);

    @Query("SELECT COUNT(*) > 0 FROM profesores WHERE username = :username AND termino = :termino")
    boolean existsByUsernameAndTermino(@Param("username") String username, @Param("termino") String termino);
}
