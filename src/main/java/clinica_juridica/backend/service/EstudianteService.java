package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.response.EstudianteResponse;
import clinica_juridica.backend.models.Semestre;
import clinica_juridica.backend.repository.EstudianteRepository;
import clinica_juridica.backend.repository.SemestreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final SemestreRepository semestreRepository;

    public EstudianteService(EstudianteRepository estudianteRepository, SemestreRepository semestreRepository) {
        this.estudianteRepository = estudianteRepository;
        this.semestreRepository = semestreRepository;
    }

    public List<EstudianteResponse> getEstudiantes(Boolean activo, Boolean conCasos) {
        if (Boolean.TRUE.equals(activo) || Boolean.TRUE.equals(conCasos)) {
            Semestre activeSemester = semestreRepository.findActiveSemester();

            if (activeSemester == null) {
                return List.of();
            }

            if (Boolean.TRUE.equals(conCasos)) {
                return estudianteRepository.findWithActiveCases(activeSemester.getTermino());
            }

            return estudianteRepository.findByTermino(activeSemester.getTermino());
        } else {
            return estudianteRepository.findAllProjected();
        }
    }
}
