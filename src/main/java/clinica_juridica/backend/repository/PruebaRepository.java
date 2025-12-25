package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Prueba;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PruebaRepository extends CrudRepository<Prueba, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM pruebas")
    List<Prueba> findAll();

    @Query("SELECT COALESCE(MAX(id_prueba), 0) FROM pruebas WHERE num_caso = :numCaso")
    Integer findMaxIdByNumCaso(String numCaso);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("INSERT INTO pruebas (id_prueba, num_caso, fecha, documento, observacion, titulo) VALUES (:id, :numCaso, :fecha, :documento, :observacion, :titulo)")
    void saveManual(Integer id, String numCaso, java.time.LocalDate fecha, String documento, String observacion,
            String titulo);
}
