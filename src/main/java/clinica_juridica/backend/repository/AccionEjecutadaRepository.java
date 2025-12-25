package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.AccionEjecutada;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccionEjecutadaRepository extends CrudRepository<AccionEjecutada, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM acciones_ejecutadas")
    List<AccionEjecutada> findAll();

    @Query("SELECT COALESCE(MAX(id_accion_ejecutada), 0) FROM acciones_ejecutadas WHERE num_caso = :numCaso")
    Integer findMaxIdByNumCaso(String numCaso);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("INSERT INTO acciones_ejecutadas (id_accion_ejecutada, id_accion, num_caso, username) VALUES (:id, :idAccion, :numCaso, :username)")
    void saveManual(Integer id, Integer idAccion, String numCaso, String username);
}
