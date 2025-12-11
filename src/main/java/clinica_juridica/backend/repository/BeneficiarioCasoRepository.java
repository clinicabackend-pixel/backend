package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.BeneficiarioCaso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiarioCasoRepository extends CrudRepository<BeneficiarioCaso, String> {
}
