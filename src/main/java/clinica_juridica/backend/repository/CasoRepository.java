package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Caso;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;
import clinica_juridica.backend.dto.response.CasoListaResponse;

@Repository
public interface CasoRepository extends CrudRepository<Caso, String> {

    @Query("SELECT c.num_caso, al.materia, u.id_usuario as cedula, u.nombre "
            +
            "FROM casos c " +
            "JOIN ambitos_legales al ON c.id_ambito_legal = al.id_ambito_legal " +
            "JOIN usuarios u ON c.id_solicitante = u.id_usuario")
    List<CasoListaResponse> findAllWithSolicitanteInfo();

    

}
