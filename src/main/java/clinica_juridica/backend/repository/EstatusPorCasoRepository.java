package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.EstatusPorCaso;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstatusPorCasoRepository extends CrudRepository<EstatusPorCaso, Integer> {
    @Override
    @Query("SELECT * FROM estatus_por_caso")
    List<EstatusPorCaso> findAll();
}
