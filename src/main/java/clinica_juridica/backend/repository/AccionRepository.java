package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Accion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import clinica_juridica.backend.dto.response.AccionResponse;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface AccionRepository extends CrudRepository<Accion, Integer> {

    @Query("SELECT id_accion, titulo, descripcion, f_registro " +
            "FROM acciones " +
            "WHERE num_caso = :numCaso")
    List<AccionResponse> findByNumCaso(@Param("numCaso") String numCaso);
}
