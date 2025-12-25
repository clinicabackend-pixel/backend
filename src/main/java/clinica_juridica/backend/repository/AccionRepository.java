package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Accion;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccionRepository extends CrudRepository<Accion, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM accion")
    List<Accion> findAll();

    @Query("SELECT COALESCE(MAX(id_accion), 0) FROM accion WHERE num_caso = :numCaso")
    Integer findMaxIdByNumCaso(String numCaso);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("INSERT INTO accion (id_accion, num_caso, titulo, descripcion, fecha_registro, fecha_ejecucion, username) VALUES (:id, :numCaso, :titulo, :descripcion, :fechaRegistro, :fechaEjecucion, :username)")
    void saveManual(Integer id, String numCaso, String titulo, String descripcion, java.time.LocalDate fechaRegistro,
            java.time.LocalDate fechaEjecucion, String username);
}
