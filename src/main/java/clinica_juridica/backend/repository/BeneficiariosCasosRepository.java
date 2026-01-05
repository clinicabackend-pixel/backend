package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.BeneficiarioCaso;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BeneficiariosCasosRepository extends CrudRepository<BeneficiarioCaso, String> {

    @Query("SELECT * FROM beneficiarios_casos WHERE num_caso = :numCaso")
    List<BeneficiarioCaso> findAllByNumCaso(String numCaso);

    @Modifying
    @Transactional
    @Query("INSERT INTO beneficiarios_casos (cedula, num_caso, tipo_beneficiario, parentesco) VALUES (:cedula, :numCaso, :tipoBeneficiario, :parentesco)")
    void saveManual(String cedula, String numCaso, String tipoBeneficiario, String parentesco);

    @Modifying
    @Transactional
    @Query("DELETE FROM beneficiarios_casos WHERE num_caso = :numCaso")
    void deleteByNumCaso(String numCaso);

    @Modifying
    @Transactional
    @Query("UPDATE beneficiarios_casos SET tipo_beneficiario = :tipoBeneficiario, parentesco = :parentesco WHERE cedula = :cedula AND num_caso = :numCaso")
    void updateManual(String cedula, String numCaso, String tipoBeneficiario, String parentesco);
}
