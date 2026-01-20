package clinica_juridica.backend.repository;

import clinica_juridica.backend.dto.projection.MateriaCountProjection;
import clinica_juridica.backend.dto.response.CasoSummaryResponse;
import clinica_juridica.backend.dto.response.ReporteResumenDto;
import clinica_juridica.backend.models.Caso;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

        @Query("SELECT DISTINCT c.num_caso, c.fecha_recepcion, c.sintesis, c.estatus, c.termino, c.cedula, s.nombre AS nombre_solicitante, c.com_amb_legal "
                        + "FROM casos c " + "JOIN solicitantes s ON c.cedula = s.cedula " + "WHERE c.cedula = :cedula")
        List<CasoSummaryResponse> findCasosBySolicitanteCedula(String cedula);

        @Query("SELECT DISTINCT c.num_caso, c.fecha_recepcion, c.sintesis, c.estatus, c.termino, c.cedula, s.nombre AS nombre_solicitante, c.com_amb_legal "
                        + "FROM casos c " + "JOIN solicitantes s ON c.cedula = s.cedula "
                        + "JOIN beneficiarios_casos bc ON c.num_caso = bc.num_caso " + "WHERE bc.cedula = :cedula")
        List<CasoSummaryResponse> findCasosByBeneficiarioCedula(String cedula);

        @Modifying
        @Query("UPDATE casos SET estatus = :estatus WHERE num_caso = :numCaso")
        void updateEstatus(String numCaso, String estatus);

        @Query("SELECT registrar_nuevo_caso(:sintesis, :tramite, :cantBeneficiarios, :idTribunal, :idCentro, :cedula, :username, :comAmbLegal)")
        String registrarNuevoCaso(String sintesis, String tramite, Integer cantBeneficiarios, Integer idTribunal,
                        Integer idCentro, String cedula, String username, Integer comAmbLegal);

        @Modifying
        @Query("UPDATE casos SET sintesis = :sintesis, cod_caso_tribunal = :codCasoTribunal, fecha_res_caso_tri = :fechaResCasoTri, fecha_crea_caso_tri = :fechaCreaCasoTri, id_tribunal = :idTribunal, com_amb_legal = :comAmbLegal WHERE num_caso = :numCaso")
        void updateManual(String numCaso, String sintesis,
                        String codCasoTribunal, LocalDate fechaResCasoTri,
                        LocalDate fechaCreaCasoTri,
                        Integer idTribunal, Integer comAmbLegal);

        // Reporte: Historial de Casos
        @Query("SELECT * FROM casos WHERE cedula = :cedula AND fecha_recepcion BETWEEN :inicio AND :fin")
        List<Caso> findCasosBySolicitanteAndFechaRange(String cedula, LocalDate inicio,
                        LocalDate fin);

        @Query("SELECT DISTINCT c.num_caso, c.fecha_recepcion, c.sintesis, c.estatus, c.termino, c.cedula, s.nombre AS nombre_solicitante, c.com_amb_legal "
                        +
                        "FROM casos c " +
                        "LEFT JOIN casos_asignados ca ON c.num_caso = ca.num_caso " +
                        "LEFT JOIN casos_supervisados cs ON c.num_caso = cs.num_caso " +
                        "LEFT JOIN solicitantes s ON c.cedula = s.cedula " +
                        "WHERE (c.fecha_recepcion BETWEEN :inicio AND :fin) " +
                        "AND (:username IS NULL OR ca.username = :username OR cs.username = :username)")
        List<CasoSummaryResponse> findCasosByDatesAndUsuario(LocalDate inicio, LocalDate fin,
                        String username);

        // Reporte: Informe Resumen
        @Query("SELECT estatus, COUNT(*) as cantidad FROM casos WHERE termino = :termino AND com_amb_legal = :comAmbLegal GROUP BY estatus")
        List<ReporteResumenDto> getResumenSemestreTipo(String termino,
                        Integer comAmbLegal);

        // Dashboard Stats
        @Query("SELECT COUNT(*) FROM casos")
        long countTotalCasos();

        @Query("SELECT COUNT(*) FROM casos WHERE estatus = :estatus")
        long countByEstatus(String estatus);

        @Query("SELECT CONCAT(mal.mat_amb_legal, ' - ', cal.cat_amb_legal) AS materia, COUNT(c.num_caso) AS cantidad " +
                        "FROM casos c " +
                        "JOIN ambito_legal al ON c.com_amb_legal = al.cod_amb_legal " +
                        "JOIN subcategoria_ambito_legal sal ON al.cod_sub_amb_legal = sal.cod_sub_amb_legal " +
                        "JOIN categoria_ambito_legal cal ON sal.cod_cat_amb_legal = cal.cod_cat_amb_legal " +
                        "JOIN materia_ambito_legal mal ON cal.cod_mat_amb_legal = mal.cod_mat_amb_legal " +
                        "GROUP BY mal.mat_amb_legal, cal.cat_amb_legal")
        List<MateriaCountProjection> countDistribucionMateria();

        @Query("SELECT CAST(EXTRACT(YEAR FROM fecha_recepcion) AS VARCHAR) AS label, COUNT(*) AS value " +
                        "FROM casos " +
                        "WHERE fecha_recepcion IS NOT NULL " +
                        "GROUP BY EXTRACT(YEAR FROM fecha_recepcion) " +
                        "ORDER BY label ASC")
        List<clinica_juridica.backend.dto.stats.StatsItem> countCasosPorAnio();
}
