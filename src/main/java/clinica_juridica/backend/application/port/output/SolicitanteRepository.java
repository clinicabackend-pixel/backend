package clinica_juridica.backend.application.port.output;

import clinica_juridica.backend.domain.entities.Solicitante;
import java.util.List;
import java.util.Optional;

public interface SolicitanteRepository {
    Solicitante save(Solicitante solicitante);
    Optional<Solicitante> findById(String id);
    List<Solicitante> findAll();
    void deleteById(String id);
}

