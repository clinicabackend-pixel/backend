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

    @Query("SELECT * FROM condicion_laboral WHERE estatus = :estatus")
    List<CondicionLaboral> findAllByEstatus(String estatus);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("UPDATE condicion_laboral SET estatus = :estatus WHERE id_condicion = :id")
    void updateStatus(Integer id, String estatus);
}
