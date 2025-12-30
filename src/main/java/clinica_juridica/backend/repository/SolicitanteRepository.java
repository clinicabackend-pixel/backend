package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Solicitante;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
}
