package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Caso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;
import clinica_juridica.backend.dto.response.CasoListResponse;

@Repository
public interface CasoRepository extends CrudRepository<Caso, String> {

        @Query("SELECT c.num_caso, al.materia, u.id_usuario as cedula, u.nombre, c.fecha_recepcion as fecha, c.estatus "
                        +
                        "FROM casos c " +
                        "JOIN ambitos_legales al ON c.id_ambito_legal = al.id_ambito_legal " +
                        "JOIN usuarios u ON c.id_solicitante = u.id_usuario")
        List<CasoListResponse> findAllWithSolicitanteInfo();

        @Query("SELECT sp_registrar_caso(:#{#caso.idSolicitante}, 1, :#{#caso.idAmbitoLegal}, :#{#caso.fechaRecepcion}, :#{#caso.estatus}, :#{#caso.sintesis}, 0)")
        String createCaso(@Param("caso") Caso caso);

        @Query("SELECT sp_actualizar_caso(:#{#caso.numCaso}, :#{#caso.idSolicitante}, 1, :#{#caso.idAmbitoLegal}, :#{#caso.fechaRecepcion}, :#{#caso.estatus}, :#{#caso.sintesis}, 0)")
        String updateCaso(@Param("caso") Caso caso);

        @Query("SELECT c.num_caso, c.fecha_recepcion, c.estatus, c.sintesis, c.cant_beneficiarios, " +
                        "u.id_usuario as id_solicitante, u.nombre as nombre_solicitante, al.materia " +
                        "FROM casos c " +
                        "JOIN usuarios u ON c.id_solicitante = u.id_usuario " +
                        "JOIN ambitos_legales al ON c.id_ambito_legal = al.id_ambito_legal " +
                        "WHERE c.num_caso = :numCaso")
        java.util.Optional<clinica_juridica.backend.dto.response.CasoBasicInfoResponse> findCasoDetalleBasic(
                        @Param("numCaso") String numCaso);

}
