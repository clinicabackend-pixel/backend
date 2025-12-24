package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Familia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("null")
public interface FamiliaRepository extends CrudRepository<Familia, String> {
}
