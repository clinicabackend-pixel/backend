package clinica_juridica.backend.service;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class AcademicoService {

    private final SemestreRepository semestreRepository;
    private final MateriaRepository materiaRepository;
    private final ProfesorRepository profesorRepository;
    private final CoordinadorRepository coordinadorRepository;
    private final EstudianteRepository estudianteRepository;
    private final SeccionRepository seccionRepository;
    private final EstudianteInscritoRepository estudianteInscritoRepository;
    private final AsignacionRepository asignacionRepository;

    public AcademicoService(SemestreRepository semestreRepository,
            MateriaRepository materiaRepository,
            ProfesorRepository profesorRepository,
            CoordinadorRepository coordinadorRepository,
            EstudianteRepository estudianteRepository,
            SeccionRepository seccionRepository,
            EstudianteInscritoRepository estudianteInscritoRepository,
            AsignacionRepository asignacionRepository) {
        this.semestreRepository = semestreRepository;
        this.materiaRepository = materiaRepository;
        this.profesorRepository = profesorRepository;
        this.coordinadorRepository = coordinadorRepository;
        this.estudianteRepository = estudianteRepository;
        this.seccionRepository = seccionRepository;
        this.estudianteInscritoRepository = estudianteInscritoRepository;
        this.asignacionRepository = asignacionRepository;
    }

    public Iterable<Materia> findAllMaterias() {
        return materiaRepository.findAll();
    }
}
