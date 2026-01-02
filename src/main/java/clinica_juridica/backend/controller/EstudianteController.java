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

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public ResponseEntity<List<EstudianteResponse>> getEstudiantes(@RequestParam(required = false) Boolean activo) {
        return ResponseEntity.ok(estudianteService.getEstudiantes(activo));
    }
}
