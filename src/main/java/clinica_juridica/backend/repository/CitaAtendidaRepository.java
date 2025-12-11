package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CitaAtendida;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaAtendidaRepository extends CrudRepository<CitaAtendida, String> {
}
