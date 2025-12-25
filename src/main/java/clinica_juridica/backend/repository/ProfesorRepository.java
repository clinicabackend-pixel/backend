package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Profesor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesorRepository extends CrudRepository<Profesor, String> {
    @Override
    @Query("SELECT * FROM profesores")
    List<Profesor> findAll();

    @Override
    @Query("SELECT * FROM profesores WHERE cedula = :cedula")
    Optional<Profesor> findById(String cedula);
}
