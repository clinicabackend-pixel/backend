package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.EstudianteResponse;
import clinica_juridica.backend.service.EstudianteService;
import clinica_juridica.backend.service.ImportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final ImportService importService;

    public EstudianteController(EstudianteService estudianteService,
            ImportService importService) {
        this.estudianteService = estudianteService;
        this.importService = importService;
    }

    @GetMapping
    public ResponseEntity<List<EstudianteResponse>> getEstudiantes(@RequestParam(required = false) Boolean activo) {
        return ResponseEntity.ok(estudianteService.getEstudiantes(activo));
    }

    @PostMapping("/importar")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<?> importarEstudiantes(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> report = importService.importEstudiantes(file);
            return ResponseEntity.ok(report);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }
}
