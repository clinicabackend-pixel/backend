package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.AccionEjecutada;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccionEjecutadaRepository extends CrudRepository<AccionEjecutada, Integer> {
    @Override
    @Query("SELECT * FROM acciones_ejecutadas")
    List<AccionEjecutada> findAll();
}
