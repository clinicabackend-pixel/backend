package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Seccion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeccionRepository extends CrudRepository<Seccion, String> {
}
