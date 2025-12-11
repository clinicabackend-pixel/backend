package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Coordinador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinadorRepository extends CrudRepository<Coordinador, String> {
}
