package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CasoAsignado;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasoAsignadoRepository extends CrudRepository<CasoAsignado, Integer> {
    @Override
    @Query("SELECT * FROM casos_asignados")
    List<CasoAsignado> findAll();
}
