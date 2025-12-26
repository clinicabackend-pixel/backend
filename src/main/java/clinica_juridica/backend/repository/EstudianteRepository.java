package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Estudiante;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends CrudRepository<Estudiante, String> {
    @Override
    @NonNull
    @Query("SELECT * FROM estudiantes")
    List<Estudiante> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM estudiantes WHERE id_estudiante = :id")
    Optional<Estudiante> findById(@NonNull String id);

    @Query("SELECT COUNT(*) > 0 FROM estudiantes WHERE username = :username AND termino = :termino")
    boolean existsByUsernameAndTermino(@Param("username") String username, @Param("termino") String termino);
}
