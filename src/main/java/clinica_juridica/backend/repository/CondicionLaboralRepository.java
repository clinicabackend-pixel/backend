package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CondicionLaboral;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondicionLaboralRepository extends CrudRepository<CondicionLaboral, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM condicion_laboral")
    List<CondicionLaboral> findAll();
}
