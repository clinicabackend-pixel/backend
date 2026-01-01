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

    @Query("SELECT * FROM semestre WHERE CURRENT_DATE BETWEEN fecha_inicio AND fecha_fin LIMIT 1")
    Semestre findActiveSemester();

    @Query("SELECT * FROM semestre WHERE :date BETWEEN fecha_inicio AND fecha_fin LIMIT 1")
    Semestre findByDate(@NonNull java.time.LocalDate date);
}
