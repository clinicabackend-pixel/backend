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

    @Modifying
    @Query("""
                INSERT INTO solicitantes (
                    cedula, nombre, sexo, nacionalidad, email, concubinato,
                    telf_casa, telf_celular, f_nacimiento, id_estado_civil, id_parroquia
                ) VALUES (
                    :cedula, :nombre, :sexo, :nacionalidad, :email, :concubinato,
                    :telfCasa, :telfCelular, :fNacimiento, :idEstadoCivil, :idParroquia
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
            @Param("idParroquia") Integer idParroquia);
}
