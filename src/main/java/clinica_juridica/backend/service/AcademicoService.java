package clinica_juridica.backend.service;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class AcademicoService {

    private final MateriaRepository materiaRepository;

    public AcademicoService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public Iterable<Materia> findAllMaterias() {
        return materiaRepository.findAll();
    }
}
