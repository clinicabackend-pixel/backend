package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Municipio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("null")
public interface MunicipioRepository extends CrudRepository<Municipio, Integer> {
}
