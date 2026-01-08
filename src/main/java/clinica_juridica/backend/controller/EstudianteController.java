package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.EstudianteResponse;
import clinica_juridica.backend.service.EstudianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final clinica_juridica.backend.service.ImportService importService;

    public EstudianteController(EstudianteService estudianteService,
            clinica_juridica.backend.service.ImportService importService) {
        this.estudianteService = estudianteService;
        this.importService = importService;
    }

    @GetMapping
    public ResponseEntity<List<EstudianteResponse>> getEstudiantes(@RequestParam(required = false) Boolean activo) {
        return ResponseEntity.ok(estudianteService.getEstudiantes(activo));
    }

    @org.springframework.web.bind.annotation.PostMapping("/importar")
    public ResponseEntity<?> importarEstudiantes(
            @org.springframework.web.bind.annotation.RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            java.util.Map<String, Object> report = importService.importEstudiantes(file);
            return ResponseEntity.ok(report);
        } catch (java.io.IOException e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }
}
