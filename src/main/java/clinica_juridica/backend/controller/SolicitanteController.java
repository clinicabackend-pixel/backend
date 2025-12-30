package clinica_juridica.backend.controller;

import clinica_juridica.backend.service.SolicitanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import clinica_juridica.backend.dto.request.SolicitanteRequest;
import clinica_juridica.backend.dto.response.SolicitanteResponse;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/solicitantes")
@SuppressWarnings("null")
@Tag(name = "Solicitantes", description = "API para la gestión de solicitantes. Roles: Todos pueden Crear/Ver/Editar. Solo COORDINADOR puede eliminar.")
public class SolicitanteController {

    private final SolicitanteService solicitanteService;

    public SolicitanteController(SolicitanteService solicitanteService) {
        this.solicitanteService = solicitanteService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los solicitantes", description = "Devuelve una lista de todos los solicitantes registrados. Roles: COORDINADOR, PROFESOR, ESTUDIANTE.")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR', 'ESTUDIANTE')")
    public ResponseEntity<List<SolicitanteResponse>> getAll() {
        return ResponseEntity.ok(solicitanteService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener solicitante por ID", description = "Busca un solicitante específico por su cédula/ID. Roles: COORDINADOR, PROFESOR, ESTUDIANTE.")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR', 'ESTUDIANTE')")
    public ResponseEntity<SolicitanteResponse> getById(@PathVariable String id) {
        return solicitanteService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear solicitante", description = "Registra un nuevo solicitante en el sistema. Roles: COORDINADOR, PROFESOR, ESTUDIANTE.")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR', 'ESTUDIANTE')")
    public ResponseEntity<String> create(@RequestBody SolicitanteRequest solicitante) {
        solicitanteService.create(solicitante);
        return ResponseEntity.ok("Solicitante creado exitosamente");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar solicitante", description = "Actualiza los datos de un solicitante existente. Roles: COORDINADOR, PROFESOR, ESTUDIANTE.")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR', 'ESTUDIANTE')")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody SolicitanteRequest solicitante) {
        if (!solicitanteService.update(id, solicitante)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Solicitante actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar solicitante", description = "Elimina un solicitante del sistema. Roles: COORDINADOR.")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<String> delete(@PathVariable String id) {
        if (!solicitanteService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Solicitante eliminado exitosamente");
    }
}
