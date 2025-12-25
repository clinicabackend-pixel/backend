package clinica_juridica.backend.controller;

import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.service.CasoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/casos")
public class CasoController {

    private final CasoService casoService;

    public CasoController(CasoService casoService) {
        this.casoService = casoService;
    }

    @GetMapping
    public ResponseEntity<List<Caso>> getAll(@RequestParam(required = false) String estatus) {
        if (estatus != null) {
            return ResponseEntity.ok(casoService.getCasosByEstatus(estatus));
        }
        return ResponseEntity.ok(casoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caso> getById(@PathVariable String id) {
        return casoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Caso caso) {
        casoService.create(caso);
        return ResponseEntity.ok("Caso creado exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody Caso caso) {
        if (casoService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        casoService.update(id, caso);
        return ResponseEntity.ok("Caso actualizado exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        if (casoService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        casoService.delete(id);
        return ResponseEntity.ok("Caso eliminado exitosamente");
    }

    @PatchMapping("/{id}/estatus")
    public ResponseEntity<String> updateEstatus(@PathVariable String id,
            @RequestBody Map<String, String> body) {
        String nuevoEstatus = body.get("estatus");
        if (nuevoEstatus == null) {
            return ResponseEntity.badRequest().body("El campo 'estatus' es obligatorio");
        }
        casoService.updateEstatus(id, nuevoEstatus);
        return ResponseEntity.ok("Estatus actualizado exitosamente");
    }
}
