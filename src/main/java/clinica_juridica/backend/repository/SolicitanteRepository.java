package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Solicitante;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitanteRepository extends CrudRepository<Solicitante, String> {
    @Override
    @Query("SELECT * FROM solicitante")
    List<Solicitante> findAll();

    @Override
    @Query("SELECT * FROM solicitante WHERE cedula = :cedula")
    Optional<Solicitante> findById(String cedula);
}
