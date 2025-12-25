package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Encuentro;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncuentroRepository extends CrudRepository<Encuentro, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM encuentros")
    List<Encuentro> findAll();

    @Query("SELECT COALESCE(MAX(id_encuentros), 0) FROM encuentros WHERE num_caso = :numCaso")
    Integer findMaxIdByNumCaso(String numCaso);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("INSERT INTO encuentros (id_encuentros, num_caso, fecha_atencion, fecha_proxima, orientacion, observacion, username) VALUES (:id, :numCaso, :fechaAtencion, :fechaProxima, :orientacion, :observacion, :username)")
    void saveManual(Integer id, String numCaso, java.time.LocalDate fechaAtencion, java.time.LocalDate fechaProxima,
            String orientacion, String observacion, String username);
}
