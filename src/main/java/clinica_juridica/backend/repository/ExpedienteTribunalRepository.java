package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.ExpedienteTribunal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import clinica_juridica.backend.dto.response.ExpedienteResponse;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface ExpedienteTribunalRepository extends CrudRepository<ExpedienteTribunal, String> {

    @Query("SELECT e.num_expediente, e.fecha_creacion, t.nombre_tribunal " +
            "FROM expediente_tribunales e " +
            "JOIN tribunales t ON e.id_tribunal = t.id_tribunal " +
            "WHERE e.id_caso = :numCaso")
    Optional<ExpedienteResponse> findByNumCaso(@Param("numCaso") String numCaso);
}
