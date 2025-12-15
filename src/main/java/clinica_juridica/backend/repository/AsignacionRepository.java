package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Asignacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import clinica_juridica.backend.dto.response.EstudianteAsignadoResponse;
import java.util.List;

@Repository
public interface AsignacionRepository extends CrudRepository<Asignacion, Integer> {

    @Query("SELECT a.id_estudiante, p.nombre, a.id_semestre as semestre, a.id_materia as materia, a.id_seccion as seccion "
            +
            "FROM asignaciones a " +
            "JOIN personal p ON a.id_estudiante = p.id_usuario " +
            "WHERE a.num_caso = :numCaso")
    List<EstudianteAsignadoResponse> findByNumCaso(@Param("numCaso") String numCaso);
}
