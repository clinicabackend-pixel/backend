package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Estado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("null")
public interface EstadoRepository extends CrudRepository<Estado, Integer> {
}
