package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Profesor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends CrudRepository<Profesor, String> {
}
