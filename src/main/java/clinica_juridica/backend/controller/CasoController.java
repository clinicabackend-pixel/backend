package clinica_juridica.backend.controller;

import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.service.CasoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

import clinica_juridica.backend.models.Accion;
import clinica_juridica.backend.models.Encuentro;
import clinica_juridica.backend.models.Documento;
import clinica_juridica.backend.models.Prueba;
import clinica_juridica.backend.dto.CasoDetalleDTO;
import clinica_juridica.backend.dto.UpdateCasoDTO;

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

    @GetMapping("/usuario/{username}/abiertos")
    public ResponseEntity<List<Caso>> getCasosAbiertosPorUsuario(@PathVariable String username) {
        return ResponseEntity.ok(casoService.getCasosAbiertosPorUsuario(username));
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
    public ResponseEntity<String> update(@PathVariable String id,
            @RequestBody UpdateCasoDTO dto) {
        try {
            casoService.update(id, dto);
            return ResponseEntity.ok("Caso actualizado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping("/{id}/detalle")
    public ResponseEntity<CasoDetalleDTO> getDetalle(@PathVariable String id) {
        return ResponseEntity.ok(casoService.getCasoDetalle(id));
    }

    @PostMapping("/{id}/acciones")
    public ResponseEntity<Void> createAccion(@PathVariable String id,
            @RequestBody clinica_juridica.backend.dto.CreateAccionDTO dto) {
        casoService.createAccion(id, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/encuentros")
    public ResponseEntity<Void> createEncuentro(@PathVariable String id,
            @RequestBody clinica_juridica.backend.dto.CreateEncuentroDTO dto) {
        casoService.createEncuentro(id, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/documentos")
    public ResponseEntity<Void> createDocumento(@PathVariable String id,
            @RequestBody clinica_juridica.backend.dto.CreateDocumentoDTO dto) {
        casoService.createDocumento(id, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/pruebas")
    public ResponseEntity<Void> createPrueba(@PathVariable String id,
            @RequestBody clinica_juridica.backend.dto.CreatePruebaDTO dto) {
        casoService.createPrueba(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/acciones")
    public ResponseEntity<List<Accion>> getAcciones(@PathVariable String id) {
        return ResponseEntity.ok(casoService.getAcciones(id));
    }

    @GetMapping("/{id}/encuentros")
    public ResponseEntity<List<Encuentro>> getEncuentros(@PathVariable String id) {
        return ResponseEntity.ok(casoService.getEncuentros(id));
    }

    @GetMapping("/{id}/documentos")
    public ResponseEntity<List<Documento>> getDocumentos(@PathVariable String id) {
        return ResponseEntity.ok(casoService.getDocumentos(id));
    }

    @GetMapping("/{id}/pruebas")
    public ResponseEntity<List<Prueba>> getPruebas(@PathVariable String id) {
        return ResponseEntity.ok(casoService.getPruebas(id));
    }

    @DeleteMapping("/{id}/acciones/{idAccion}")
    public ResponseEntity<Void> deleteAccion(@PathVariable String id, @PathVariable Integer idAccion) {
        casoService.deleteAccion(id, idAccion);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/encuentros/{idEncuentro}")
    public ResponseEntity<Void> deleteEncuentro(@PathVariable String id, @PathVariable Integer idEncuentro) {
        casoService.deleteEncuentro(id, idEncuentro);
        return ResponseEntity.noContent().build();
    }
}
