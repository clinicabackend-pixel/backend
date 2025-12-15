package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.BeneficiarioCaso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import clinica_juridica.backend.dto.response.BeneficiarioResponse;
import java.util.List;

@Repository
public interface BeneficiarioCasoRepository extends CrudRepository<BeneficiarioCaso, String> {

    @Query("SELECT sp_agregar_beneficiario(:idBeneficiario, :numCaso, :tipo, :parentesco)")
    String addBeneficiario(@Param("idBeneficiario") String idBeneficiario,
            @Param("numCaso") String numCaso,
            @Param("tipo") String tipo,
            @Param("parentesco") String parentesco);

    @Query("SELECT bc.id_beneficiario, u.nombre, bc.tipo_beneficiario as tipo, bc.parentesco " +
            "FROM beneficiarios_casos bc " +
            "JOIN usuarios u ON bc.id_beneficiario = u.id_usuario " +
            "WHERE bc.num_caso = :numCaso")
    List<BeneficiarioResponse> findByNumCaso(@Param("numCaso") String numCaso);
}
