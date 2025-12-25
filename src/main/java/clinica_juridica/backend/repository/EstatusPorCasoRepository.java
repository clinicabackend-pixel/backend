package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.EstatusPorCaso;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jdbc.repository.query.Modifying;

import java.util.List;

@Repository
public interface EstatusPorCasoRepository extends CrudRepository<EstatusPorCaso, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM estatus_por_caso")
    List<EstatusPorCaso> findAll();

    @Query("SELECT COALESCE(MAX(id_est_caso), 0) FROM estatus_por_caso WHERE num_caso = :numCaso")
    Integer findMaxIdByNumCaso(String numCaso);

    @Modifying
    @Query("INSERT INTO estatus_por_caso (id_est_caso, num_caso, fecha_cambio, estatus, observacion) VALUES (:idEstCaso, :numCaso, :fechaCambio, :estatus, NULL)")
    void saveNewStatus(Integer idEstCaso, String numCaso, java.time.LocalDate fechaCambio, String estatus);
}
