package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.ProfesorResponse;
import clinica_juridica.backend.service.ProfesorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/profesores")
@Tag(name = "Profesores", description = "Gestión de profesores")
public class ProfesorController {

    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @GetMapping
    @PreAuthorize("hasRole('COORDINADOR') or hasRole('PROFESOR') or hasRole('ADMIN')")
    @Operation(summary = "Obtener lista de profesores", description = "Devuelve una lista de todos los profesores registrados. Requiere rol COORDINADOR, ADMIN o PROFESOR.")
    public ResponseEntity<List<ProfesorResponse>> getProfesores() {
        return ResponseEntity.ok(profesorService.getProfesores());
    }
}
