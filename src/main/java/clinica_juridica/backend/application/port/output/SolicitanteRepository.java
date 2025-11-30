package clinica_juridica.backend.application.port.output;

import java.util.List;
import java.util.Optional;

import clinica_juridica.backend.domain.models.Solicitante;

public interface SolicitanteRepository {
    Solicitante save(Solicitante solicitante);
    Optional<Solicitante> findById(String id);
    List<Solicitante> findAll();
    void deleteById(String id);
}

