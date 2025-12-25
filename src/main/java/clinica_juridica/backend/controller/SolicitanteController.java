package clinica_juridica.backend.controller;

import clinica_juridica.backend.models.Solicitante;
import clinica_juridica.backend.repository.SolicitanteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitantes")
@SuppressWarnings("null")
public class SolicitanteController {

    private final SolicitanteRepository solicitanteRepository;

    public SolicitanteController(SolicitanteRepository solicitanteRepository) {
        this.solicitanteRepository = solicitanteRepository;
    }

    @GetMapping
    public ResponseEntity<List<Solicitante>> getAll() {
        return ResponseEntity.ok(solicitanteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitante> getById(@PathVariable String id) {
        return solicitanteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Solicitante solicitante) {
        solicitanteRepository.save(solicitante);
        return ResponseEntity.ok("Solicitante creado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Solicitante solicitante) {
        if (solicitanteRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        solicitante.setCedula(id); // Ensure ID consistency
        solicitanteRepository.save(solicitante); // CrudRepository uses save for update if ID exists
        return ResponseEntity.ok("Solicitante actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        if (solicitanteRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        solicitanteRepository.deleteById(id);
        return ResponseEntity.ok("Solicitante eliminado exitosamente");
    }
}
