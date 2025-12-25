package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.EncuentroAtendido;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncuentroAtendidoRepository extends CrudRepository<EncuentroAtendido, Integer> {
    @Override
    @Query("SELECT * FROM encuentros_atendidos")
    List<EncuentroAtendido> findAll();
}
