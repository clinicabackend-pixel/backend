package clinica_juridica.backend.application.port.output;

import clinica_juridica.backend.domain.entities.Seccion;
import java.util.List;
import java.util.Optional;

public interface SeccionRepository {
    Seccion save(Seccion seccion);
    Optional<Seccion> findById(Integer idMateria, Integer idSeccion, Integer idSemestre);
    List<Seccion> findAll();
    void delete(Integer idMateria, Integer idSeccion, Integer idSemestre);
}

