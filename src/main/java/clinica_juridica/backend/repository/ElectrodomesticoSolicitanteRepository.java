package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.ElectrodomesticoSolicitante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectrodomesticoSolicitanteRepository extends CrudRepository<ElectrodomesticoSolicitante, String> {
}
