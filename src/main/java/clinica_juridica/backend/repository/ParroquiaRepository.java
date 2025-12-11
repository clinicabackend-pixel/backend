package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Parroquia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParroquiaRepository extends CrudRepository<Parroquia, Integer> {
}
