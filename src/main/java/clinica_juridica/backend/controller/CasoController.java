package clinica_juridica.backend.controller;

import clinica_juridica.backend.service.CasoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.dto.request.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/casos")
@Tag(name = "Casos", description = "API para la gestión de casos legales")
public class CasoController {

    private final CasoService casoService;

    public CasoController(CasoService casoService) {
        this.casoService = casoService;
    }

    @Operation(summary = "Obtener casos (Resumen)", description = "Recupera una lista ligera de casos filtrada por estatus, usuario asignado o término (semestre).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de casos recuperada exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<CasoSummaryResponse>> getAll(
            @Parameter(description = "Estatus del caso para filtrar") @RequestParam(required = false) String estatus,
            @Parameter(description = "Nombre de usuario para filtrar sus casos asignados") @RequestParam(required = false) String username,
            @Parameter(description = "Término o semestre (ej: 2024-1)") @RequestParam(required = false) String termino) {

        // Normalizar strings vacíos a null para que la query funcione correctamente
        String estatusFilter = (estatus != null && !estatus.isBlank()) ? estatus : null;
        String usernameFilter = (username != null && !username.isBlank()) ? username : null;
        String terminoFilter = (termino != null && !termino.isBlank()) ? termino : null;

        return ResponseEntity.ok(casoService.getCasosSummaryByFilters(estatusFilter, usernameFilter, terminoFilter));
    }

    @Operation(summary = "Obtener detalle del caso", description = "Recupera la información completa de un caso por su ID, incluyendo acciones, encuentros, documentos y pruebas asociados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle del caso encontrado"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CasoDetalleResponse> getById(
            @Parameter(description = "ID del caso") @PathVariable String id) {
        try {
            return ResponseEntity.ok(casoService.getCasoDetalle(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear nuevo caso", description = "Crea un nuevo caso legal en el sistema.")
    @ApiResponse(responseCode = "200", description = "Caso creado exitosamente")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody CasoCreateRequest request) {
        casoService.create(request);
        return ResponseEntity.ok("Caso creado exitosamente");
    }

    @Operation(summary = "Actualizar caso", description = "Actualiza la información de un caso existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caso actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @Parameter(description = "ID del caso a actualizar") @PathVariable String id,
            @RequestBody CasoUpdateRequest dto) {
        try {
            casoService.update(id, dto);
            return ResponseEntity.ok("Caso actualizado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar caso", description = "Elimina un caso del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caso eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID del caso a eliminar") @PathVariable String id) {
        if (casoService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        casoService.delete(id);
        return ResponseEntity.ok("Caso eliminado exitosamente");
    }

    @Operation(summary = "Actualizar estatus del caso", description = "Actualiza el estatus de un caso específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatus actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, falta el campo 'estatus'")
    })
    @PatchMapping("/{id}/estatus")
    public ResponseEntity<String> updateEstatus(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @RequestBody Map<String, String> body) {
        String nuevoEstatus = body.get("estatus");
        if (nuevoEstatus == null) {
            return ResponseEntity.badRequest().body("El campo 'estatus' es obligatorio");
        }
        casoService.updateEstatus(id, nuevoEstatus);
        return ResponseEntity.ok("Estatus actualizado exitosamente");
    }

    // Sub-recursos (Acciones, Encuentros, Documentos, Pruebas)

    @Operation(summary = "Crear acción", description = "Registra una nueva acción asociada a un caso.")
    @ApiResponse(responseCode = "200", description = "Acción creada exitosamente")
    @PostMapping("/{id}/acciones")
    public void createAccion(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @RequestBody AccionCreateRequest dto) {
        casoService.createAccion(id, dto);
    }

    @Operation(summary = "Crear encuentro", description = "Programa un nuevo encuentro asociado a un caso.")
    @ApiResponse(responseCode = "200", description = "Encuentro creado exitosamente")
    @PostMapping("/{id}/encuentros")
    public void createEncuentro(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @RequestBody EncuentroCreateRequest dto) {
        casoService.createEncuentro(id, dto);
    }

    @Operation(summary = "Crear documento", description = "Sube o registra un nuevo documento asociado a un caso.")
    @ApiResponse(responseCode = "200", description = "Documento creado exitosamente")
    @PostMapping("/{id}/documentos")
    public void createDocumento(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @RequestBody DocumentoCreateRequest dto) {
        casoService.createDocumento(id, dto);
    }

    @Operation(summary = "Crear prueba", description = "Registra una nueva prueba asociada a un caso.")
    @ApiResponse(responseCode = "200", description = "Prueba creada exitosamente")
    @PostMapping("/{id}/pruebas")
    public void createPrueba(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @RequestBody PruebaCreateRequest dto) {
        casoService.createPrueba(id, dto);
    }

    @Operation(summary = "Obtener acciones del caso", description = "Lista todas las acciones registradas para un caso.")
    @ApiResponse(responseCode = "200", description = "Lista de acciones recuperada exitosamente")
    @GetMapping("/{id}/acciones")
    public ResponseEntity<List<AccionResponse>> getAcciones(
            @Parameter(description = "ID del caso") @PathVariable String id) {
        return ResponseEntity.ok(casoService.getAcciones(id));
    }

    @Operation(summary = "Obtener encuentros del caso", description = "Lista todos los encuentros programados para un caso.")
    @ApiResponse(responseCode = "200", description = "Lista de encuentros recuperada exitosamente")
    @GetMapping("/{id}/encuentros")
    public ResponseEntity<List<EncuentroResponse>> getEncuentros(
            @Parameter(description = "ID del caso") @PathVariable String id) {
        return ResponseEntity.ok(casoService.getEncuentros(id));
    }

    @Operation(summary = "Obtener documentos del caso", description = "Lista todos los documentos asociados a un caso.")
    @ApiResponse(responseCode = "200", description = "Lista de documentos recuperada exitosamente")
    @GetMapping("/{id}/documentos")
    public ResponseEntity<List<DocumentoResponse>> getDocumentos(
            @Parameter(description = "ID del caso") @PathVariable String id) {
        return ResponseEntity.ok(casoService.getDocumentos(id));
    }

    @Operation(summary = "Obtener pruebas del caso", description = "Lista todas las pruebas asociadas a un caso.")
    @ApiResponse(responseCode = "200", description = "Lista de pruebas recuperada exitosamente")
    @GetMapping("/{id}/pruebas")
    public ResponseEntity<List<PruebaResponse>> getPruebas(
            @Parameter(description = "ID del caso") @PathVariable String id) {
        return ResponseEntity.ok(casoService.getPruebas(id));
    }

    @Operation(summary = "Eliminar acción", description = "Elimina una acción específica de un caso.")
    @ApiResponse(responseCode = "204", description = "Acción eliminada exitosamente")
    @DeleteMapping("/{id}/acciones/{idAccion}")
    public ResponseEntity<Void> deleteAccion(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @Parameter(description = "ID de la acción a eliminar") @PathVariable Integer idAccion) {
        casoService.deleteAccion(id, idAccion);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Eliminar encuentro", description = "Elimina un encuentro específico de un caso.")
    @ApiResponse(responseCode = "204", description = "Encuentro eliminado exitosamente")
    @DeleteMapping("/{id}/encuentros/{idEncuentro}")
    public ResponseEntity<Void> deleteEncuentro(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @Parameter(description = "ID del encuentro a eliminar") @PathVariable Integer idEncuentro) {
        casoService.deleteEncuentro(id, idEncuentro);
        return ResponseEntity.noContent().build();
    }
}
