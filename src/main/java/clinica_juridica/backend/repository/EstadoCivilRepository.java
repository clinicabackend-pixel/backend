package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.EstadoCivil;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoCivilRepository extends CrudRepository<EstadoCivil, Integer> {
    @Override
    @Query("SELECT * FROM estado_civil")
    List<EstadoCivil> findAll();
}
