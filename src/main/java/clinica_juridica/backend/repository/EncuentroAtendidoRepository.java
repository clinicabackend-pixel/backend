package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.EncuentroAtendido;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncuentroAtendidoRepository extends CrudRepository<EncuentroAtendido, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM encuentros_atendidos")
    List<EncuentroAtendido> findAll();

    @Query("SELECT COALESCE(MAX(id_encuentro_atendido), 0) FROM encuentros_atendidos WHERE num_caso = :numCaso")
    Integer findMaxIdByNumCaso(String numCaso);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("INSERT INTO encuentros_atendidos (id_encuentro_atendido, id_encuentro, num_caso, username) VALUES (:id, :idEncuentro, :numCaso, :username)")
    void saveManual(Integer id, Integer idEncuentro, String numCaso, String username);
}
