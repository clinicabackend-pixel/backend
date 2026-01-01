package clinica_juridica.backend.controller;

import clinica_juridica.backend.repository.EstudianteRepository;
import clinica_juridica.backend.repository.SemestreRepository;
import clinica_juridica.backend.dto.projection.EstudianteInfoProjection;
import clinica_juridica.backend.models.Semestre;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteRepository estudianteRepository;
    private final SemestreRepository semestreRepository;

    public EstudianteController(EstudianteRepository estudianteRepository, SemestreRepository semestreRepository) {
        this.estudianteRepository = estudianteRepository;
        this.semestreRepository = semestreRepository;
    }

    @GetMapping("/activos")
    public ResponseEntity<List<EstudianteInfoProjection>> getEstudiantesActivos() {
        Semestre activeSemester = semestreRepository.findActiveSemester();

        if (activeSemester == null) {
            // Fallback strategy or return empty
            // For now, let's try to find the "latest" semester by ID/Termino descending if
            // no active found is too complex,
            // but let's stick to returning empty list if no active semester implies no
            // active operations.
            return ResponseEntity.ok(List.of());
        }

        List<EstudianteInfoProjection> estudiantes = estudianteRepository.findByTermino(activeSemester.getTermino());
        return ResponseEntity.ok(estudiantes);
    }
}
