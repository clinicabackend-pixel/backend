package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Solicitante;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

@Repository
public interface SolicitanteRepository extends CrudRepository<Solicitante, String> {
    @Override
    @NonNull
    @Query("SELECT * FROM solicitantes")
    List<Solicitante> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM solicitantes WHERE cedula = :cedula")
    Optional<Solicitante> findById(@NonNull String cedula);

    @Query("SELECT DISTINCT s.* FROM solicitantes s JOIN casos c ON s.cedula = c.cedula WHERE c.estatus = 'ABIERTO'")
    List<Solicitante> findSolicitantesConCasosActivos();

    @Query("SELECT DISTINCT s.* FROM solicitantes s JOIN casos c ON s.cedula = c.cedula")
    List<Solicitante> findSolicitantesTitulares();

    @Query("SELECT DISTINCT s.* FROM solicitantes s JOIN beneficiarios_casos bc ON s.cedula = bc.cedula")
    List<Solicitante> findBeneficiarios();

    @Query("SELECT DISTINCT s.* FROM solicitantes s JOIN beneficiarios_casos bc ON s.cedula = bc.cedula JOIN casos c ON bc.num_caso = c.num_caso WHERE c.estatus = 'ABIERTO'")
    List<Solicitante> findBeneficiariosActivos();

    @Query("""
                SELECT DISTINCT s.* FROM solicitantes s
                LEFT JOIN casos c ON s.cedula = c.cedula
                LEFT JOIN beneficiarios_casos bc ON s.cedula = bc.cedula
                LEFT JOIN casos cb ON bc.num_caso = cb.num_caso
                WHERE c.estatus = 'ABIERTO' OR cb.estatus = 'ABIERTO'
            """)
    List<Solicitante> findParticipantesActivos();

    @Modifying
    @Query("""
                INSERT INTO solicitantes (
                    cedula, nombre, sexo, nacionalidad, email, concubinato,
                    telf_casa, telf_celular, f_nacimiento, estado_civil, id_parroquia,
                    condicion_laboral, condicion_actividad, nivel_educativo
                ) VALUES (
                    :cedula, :nombre, :sexo, :nacionalidad, :email, :concubinato,
                    :telfCasa, :telfCelular, :fNacimiento, :estadoCivil, :idParroquia,
                    :condicionLaboral, :condicionActividad, :nivelEducativo
                )
            """)
    void insertSolicitante(
            @Param("cedula") String cedula,
            @Param("nombre") String nombre,
            @Param("sexo") String sexo,
            @Param("nacionalidad") String nacionalidad,
            @Param("email") String email,
            @Param("concubinato") String concubinato,
            @Param("telfCasa") String telfCasa,
            @Param("telfCelular") String telfCelular,
            @Param("fNacimiento") java.time.LocalDate fNacimiento,
            @Param("estadoCivil") String estadoCivil,
            @Param("idParroquia") Integer idParroquia,
            @Param("condicionLaboral") String condicionLaboral,
            @Param("condicionActividad") String condicionActividad,
            @Param("nivelEducativo") String nivelEducativo);

    // --- REPORT Stats Generic Queries ---

    @Query("SELECT p.nombre_parroquia AS label, COUNT(s.cedula) AS value FROM solicitantes s JOIN parroquias p ON s.id_parroquia = p.id_parroquia GROUP BY p.nombre_parroquia ORDER BY COUNT(s.cedula) DESC")
    List<clinica_juridica.backend.dto.stats.StatsItem> countSolicitantesPorParroquia();

    @Query("SELECT e.nombre_estado AS label, COUNT(s.cedula) AS value FROM solicitantes s JOIN parroquias p ON s.id_parroquia = p.id_parroquia JOIN municipios m ON p.id_municipio = m.id_municipio JOIN estados e ON m.id_estado = e.id_estado GROUP BY e.nombre_estado ORDER BY COUNT(s.cedula) DESC")
    List<clinica_juridica.backend.dto.stats.StatsItem> countSolicitantesPorEstado();

    @Query("SELECT s.sexo AS label, COUNT(s.cedula) AS value FROM solicitantes s GROUP BY s.sexo")
    List<clinica_juridica.backend.dto.stats.StatsItem> countSolicitantesPorSexo();

    // Stats for "Total Beneficiarios" (Direct vs Indirect)
    // Direct: Number of Solicitantes + Number of people registered in
    // beneficiarios_casos
    @Query("SELECT (SELECT COUNT(*) FROM solicitantes) + (SELECT COUNT(*) FROM beneficiarios_casos)")
    Long countTotalDirectBeneficiaries();

    // Indirect: Usually estimated. For now we will return 0 or do a simple
    // multiplier logic in service.
    // Or we can count distinct families? Let's just create a query that helps.
    // Actually, "Indirectos" is often estimated. We can just keep it 0 or mock in
    // service, but let's expose explicit count.
}
