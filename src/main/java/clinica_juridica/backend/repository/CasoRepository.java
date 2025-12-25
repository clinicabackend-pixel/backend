package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Caso;
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

    @Modifying
    @Query("UPDATE casos SET estatus = :estatus WHERE num_caso = :numCaso")
    void updateEstatus(String numCaso, String estatus);

    @Query("SELECT registrar_nuevo_caso(:sintesis, :tramite, :cantBeneficiarios, :idTribunal, :termino, :idCentro, :cedula, :username, :comAmbLegal)")
    String registrarNuevoCaso(String sintesis, String tramite, Integer cantBeneficiarios, Integer idTribunal,
            String termino, Integer idCentro, String cedula, String username, Integer comAmbLegal);
}
