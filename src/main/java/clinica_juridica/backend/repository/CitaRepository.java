package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Cita;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import clinica_juridica.backend.dto.response.CitaResponse;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface CitaRepository extends CrudRepository<Cita, String> {

    @Query("SELECT c.fecha, c.estado, c.orientacion, c.tramite, p.nombre as nombreAtendio " +
            "FROM citas c " +
            "LEFT JOIN citas_atendidas ca ON c.num_caso = ca.num_caso AND c.fecha = ca.fecha " +
            "LEFT JOIN personal p ON ca.id_usuario = p.id_usuario " +
            "WHERE c.num_caso = :numCaso")
    List<CitaResponse> findByNumCaso(@Param("numCaso") String numCaso);
}
