package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.dto.response.CasoSummaryResponse;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jdbc.repository.query.Modifying;
import java.util.List;
import java.util.Optional;

@Repository
public interface CasoRepository extends CrudRepository<Caso, String> {
        @Override
        @NonNull
        @Query("SELECT * FROM casos")
        List<Caso> findAll();

        @Override
        @NonNull
        @Query("SELECT * FROM casos WHERE num_caso = :numCaso")
        Optional<Caso> findById(@NonNull String numCaso);

        @Query("SELECT * FROM casos WHERE estatus = :estatus")
        List<Caso> findAllByEstatus(String estatus);

        @Query("SELECT DISTINCT c.num_caso, c.fecha_recepcion, c.sintesis, c.estatus, c.termino, c.cedula, s.nombre AS nombre_solicitante, c.com_amb_legal "
                        +
                        "FROM casos c " +
                        "LEFT JOIN casos_asignados ca ON c.num_caso = ca.num_caso " +
                        "LEFT JOIN casos_supervisados cs ON c.num_caso = cs.num_caso " +
                        "LEFT JOIN solicitantes s ON c.cedula = s.cedula " +
                        "WHERE (:estatus IS NULL OR c.estatus = :estatus) " +
                        "AND (:username IS NULL OR (ca.username = :username OR cs.username = :username)) " +
                        "AND (:termino IS NULL OR c.termino = :termino)")
        List<CasoSummaryResponse> findAllByFilters(String estatus, String username, String termino);

        @Modifying
        @Query("UPDATE casos SET estatus = :estatus WHERE num_caso = :numCaso")
        void updateEstatus(String numCaso, String estatus);

        @Query("SELECT registrar_nuevo_caso(:sintesis, :tramite, :cantBeneficiarios, :idTribunal, :idCentro, :cedula, :username, :comAmbLegal)")
        String registrarNuevoCaso(String sintesis, String tramite, Integer cantBeneficiarios, Integer idTribunal,
                        Integer idCentro, String cedula, String username, Integer comAmbLegal);

        @Modifying
        @Query("UPDATE casos SET sintesis = :sintesis, cod_caso_tribunal = :codCasoTribunal, fecha_res_caso_tri = :fechaResCasoTri, fecha_crea_caso_tri = :fechaCreaCasoTri, id_tribunal = :idTribunal, com_amb_legal = :comAmbLegal WHERE num_caso = :numCaso")
        void updateManual(String numCaso, String sintesis,
                        String codCasoTribunal, java.time.LocalDate fechaResCasoTri,
                        java.time.LocalDate fechaCreaCasoTri,
                        Integer idTribunal, Integer comAmbLegal);
}
