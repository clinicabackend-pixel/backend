package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CambioEstatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CambioEstatusRepository extends CrudRepository<CambioEstatus, String> {
}
