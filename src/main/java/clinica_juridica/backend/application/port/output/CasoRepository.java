package clinica_juridica.backend.application.port.output;

import clinica_juridica.backend.domain.entities.Caso;
import java.util.List;
import java.util.Optional;

public interface CasoRepository {
    Caso save(Caso caso);
    Optional<Caso> findById(String id);
    List<Caso> findAll();
    void deleteById(String id);
}

