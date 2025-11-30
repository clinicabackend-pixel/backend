package clinica_juridica.backend.application.port.output;

import java.util.List;
import java.util.Optional;

import clinica_juridica.backend.domain.models.Estudiante;

public interface EstudianteRepository {
    Estudiante save(Estudiante estudiante);
    Optional<Estudiante> findById(String id);
    List<Estudiante> findAll();
    void deleteById(String id);
}

