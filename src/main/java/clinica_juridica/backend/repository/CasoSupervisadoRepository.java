package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CasoSupervisado;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasoSupervisadoRepository extends CrudRepository<CasoSupervisado, Integer> {
    @Override
    @Query("SELECT * FROM casos_supervisados")
    List<CasoSupervisado> findAll();
}
