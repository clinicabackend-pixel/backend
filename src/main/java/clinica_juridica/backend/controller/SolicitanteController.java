package clinica_juridica.backend.controller;

import clinica_juridica.backend.models.Solicitante;
import clinica_juridica.backend.repository.SolicitanteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/solicitantes")
@SuppressWarnings("null")
@Tag(name = "Solicitantes", description = "API para la gestión de solicitantes. Roles requeridos: Usuario autenticado.")
public class SolicitanteController {

    private final SolicitanteRepository solicitanteRepository;

    public SolicitanteController(SolicitanteRepository solicitanteRepository) {
        this.solicitanteRepository = solicitanteRepository;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los solicitantes", description = "Devuelve una lista de todos los solicitantes registrados.")
    public ResponseEntity<List<Solicitante>> getAll() {
        return ResponseEntity.ok(solicitanteRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener solicitante por ID", description = "Busca un solicitante específico por su cédula/ID.")
    public ResponseEntity<Solicitante> getById(@PathVariable String id) {
        return solicitanteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear solicitante", description = "Registra un nuevo solicitante en el sistema.")
    public ResponseEntity<String> create(@RequestBody Solicitante solicitante) {
        solicitanteRepository.save(solicitante);
        return ResponseEntity.ok("Solicitante creado exitosamente");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar solicitante", description = "Actualiza los datos de un solicitante existente.")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Solicitante solicitante) {
        if (solicitanteRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        solicitante.setCedula(id); // Ensure ID consistency
        solicitanteRepository.save(solicitante); // CrudRepository uses save for update if ID exists
        return ResponseEntity.ok("Solicitante actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar solicitante", description = "Elimina un solicitante del sistema.")
    public ResponseEntity<String> delete(@PathVariable String id) {
        if (solicitanteRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        solicitanteRepository.deleteById(id);
        return ResponseEntity.ok("Solicitante eliminado exitosamente");
    }
}
