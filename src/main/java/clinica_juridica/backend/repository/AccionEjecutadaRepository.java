package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.AccionEjecutada;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jdbc.repository.query.Modifying;

import java.util.List;

@Repository
public interface AccionEjecutadaRepository extends CrudRepository<AccionEjecutada, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM acciones_ejecutadas")
    List<AccionEjecutada> findAll();

    @Modifying
    @Query("INSERT INTO acciones_ejecutadas (id_accion, num_caso, username) VALUES (:idAccion, :numCaso, :username)")
    void saveManual(Integer idAccion, String numCaso, String username);

    @Modifying
    @Query("DELETE FROM acciones_ejecutadas WHERE num_caso = :numCaso AND id_accion = :idAccion")
    void deleteByNumCasoAndIdAccion(String numCaso, Integer idAccion);

    @Query("SELECT COUNT(*) > 0 FROM acciones_ejecutadas WHERE num_caso = :numCaso AND id_accion = :idAccion AND username = :username")
    boolean existsByNumCasoAndIdAccionAndUsername(String numCaso, Integer idAccion, String username);
}
