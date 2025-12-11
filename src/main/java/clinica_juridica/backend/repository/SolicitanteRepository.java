package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Solicitante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitanteRepository extends CrudRepository<Solicitante, String> {
}
