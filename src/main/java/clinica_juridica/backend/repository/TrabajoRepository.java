package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Trabajo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajoRepository extends CrudRepository<Trabajo, String> {
}
