package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Estudiante;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends CrudRepository<Estudiante, String> {
    @Override
    @Query("SELECT * FROM estudiantes")
    List<Estudiante> findAll();

    @Override
    @Query("SELECT * FROM estudiantes WHERE id_estudiante = :id")
    Optional<Estudiante> findById(String id);
}
