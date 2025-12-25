package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Semestre;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemestreRepository extends CrudRepository<Semestre, String> {
    @Override
    @NonNull
    @Query("SELECT * FROM semestre")
    List<Semestre> findAll();
}
