package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Materia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends CrudRepository<Materia, Integer> {
}
