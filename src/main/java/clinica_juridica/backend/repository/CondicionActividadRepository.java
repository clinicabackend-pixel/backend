package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CondicionActividad;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondicionActividadRepository extends CrudRepository<CondicionActividad, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM condicion_actividad")
    List<CondicionActividad> findAll();

    @Query("SELECT * FROM condicion_actividad WHERE estatus = :estatus")
    List<CondicionActividad> findAllByEstatus(String estatus);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("UPDATE condicion_actividad SET estatus = :estatus WHERE id_condicion_actividad = :id")
    void updateStatus(Integer id, String estatus);
}
