package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Centro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("null")
public interface CentroRepository extends CrudRepository<Centro, Integer> {
}
