package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Tribunal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("null")
public interface TribunalRepository extends CrudRepository<Tribunal, Integer> {
}
