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
                    telf_casa, telf_celular, f_nacimiento, id_estado_civil, id_parroquia,
                    id_condicion, id_condicion_actividad, id_nivel
                ) VALUES (
                    :cedula, :nombre, :sexo, :nacionalidad, :email, :concubinato,
                    :telfCasa, :telfCelular, :fNacimiento, :idEstadoCivil, :idParroquia,
                    :idCondicion, :idCondicionActividad, :idNivel
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
            @Param("idEstadoCivil") Integer idEstadoCivil,
            @Param("idParroquia") Integer idParroquia,
            @Param("idCondicion") Integer idCondicion,
            @Param("idCondicionActividad") Integer idCondicionActividad,
            @Param("idNivel") Integer idNivel);
}
