package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Vivienda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("null")
public interface ViviendaRepository extends CrudRepository<Vivienda, String> {
}
