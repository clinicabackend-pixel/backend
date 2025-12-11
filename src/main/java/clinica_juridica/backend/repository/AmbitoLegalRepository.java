package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.AmbitoLegal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbitoLegalRepository extends CrudRepository<AmbitoLegal, Integer> {
}
