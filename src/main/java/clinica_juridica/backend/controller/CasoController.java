package clinica_juridica.backend.controller;

import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.repository.CasoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/casos")
public class CasoController {

    private final CasoRepository casoRepository;

    public CasoController(CasoRepository casoRepository) {
        this.casoRepository = casoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Caso>> getAll() {
        return ResponseEntity.ok(casoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caso> getById(@PathVariable String id) {
        return casoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Caso caso) {
        casoRepository.save(caso);
        return ResponseEntity.ok("Caso creado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Caso caso) {
        if (casoRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        caso.setNumCaso(id);
        casoRepository.save(caso); // CrudRepository uses save for update if ID exists
        return ResponseEntity.ok("Caso actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        if (casoRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        casoRepository.deleteById(id);
        return ResponseEntity.ok("Caso eliminado exitosamente");
    }
}
