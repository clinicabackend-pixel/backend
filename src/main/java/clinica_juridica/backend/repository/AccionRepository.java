package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Accion;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jdbc.repository.query.Modifying;

import java.util.List;

@Repository
public interface AccionRepository extends CrudRepository<Accion, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM accion")
    List<Accion> findAll();

    @Query("SELECT COALESCE(MAX(id_accion), 0) FROM accion WHERE num_caso = :numCaso")
    Integer findMaxIdByNumCaso(String numCaso);

    @Modifying
    @Query("INSERT INTO accion (id_accion, num_caso, titulo, descripcion, fecha_registro, fecha_ejecucion, username) VALUES (:id, :numCaso, :titulo, :descripcion, :fechaRegistro, :fechaEjecucion, :username)")
    void saveManual(Integer id, String numCaso, String titulo, String descripcion, java.time.LocalDate fechaRegistro,
            java.time.LocalDate fechaEjecucion, String username);

    @Query("SELECT * FROM accion WHERE num_caso = :numCaso")
    List<Accion> findAllByNumCaso(String numCaso);

    @Modifying
    @Query("DELETE FROM accion WHERE num_caso = :numCaso AND id_accion = :id")
    void deleteByNumCasoAndIdAccion(String numCaso, Integer id);

    @Modifying
    @Query("UPDATE accion SET fecha_ejecucion = :fecha WHERE num_caso = :numCaso AND id_accion = :id")
    void updateFechaEjecucion(String numCaso, Integer id, java.time.LocalDate fecha);

    @Query("SELECT * FROM accion WHERE num_caso = :numCaso AND id_accion = :id")
    java.util.Optional<Accion> findByNumCasoAndIdAccion(String numCaso, Integer id);
}
