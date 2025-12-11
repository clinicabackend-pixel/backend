package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Caso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasoRepository extends CrudRepository<Caso, String> {
}
