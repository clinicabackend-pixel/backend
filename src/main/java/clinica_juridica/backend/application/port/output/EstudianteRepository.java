package clinica_juridica.backend.application.port.output;

import clinica_juridica.backend.domain.entities.Estudiante;
import java.util.List;
import java.util.Optional;

public interface EstudianteRepository {
    Estudiante save(Estudiante estudiante);
    Optional<Estudiante> findById(String id);
    List<Estudiante> findAll();
    void deleteById(String id);
}

