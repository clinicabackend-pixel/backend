package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Semestre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemestreRepository extends CrudRepository<Semestre, Integer> {
}
