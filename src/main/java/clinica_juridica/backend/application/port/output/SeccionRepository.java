package clinica_juridica.backend.application.port.output;

import java.util.List;
import java.util.Optional;

import clinica_juridica.backend.domain.models.Seccion;

public interface SeccionRepository {
    Seccion save(Seccion seccion);
    Optional<Seccion> findById(Integer idMateria, Integer idSeccion, Integer idSemestre);
    List<Seccion> findAll();
    void delete(Integer idMateria, Integer idSeccion, Integer idSemestre);
}

