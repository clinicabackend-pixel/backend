package clinica_juridica.backend.controller;

import clinica_juridica.backend.service.CasoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import clinica_juridica.backend.dto.request.AccionCreateRequest;
import clinica_juridica.backend.dto.request.AccionUpdateRequest;
import clinica_juridica.backend.dto.request.BeneficiarioCreateRequest;
import clinica_juridica.backend.dto.request.BeneficiarioUpdateRequest;
import clinica_juridica.backend.dto.request.CasoAsignacionRequest;
import clinica_juridica.backend.dto.request.CasoCreateRequest;
import clinica_juridica.backend.dto.request.CasoEstatusUpdateRequest;
import clinica_juridica.backend.dto.request.CasoSupervisionRequest;
import clinica_juridica.backend.dto.request.CasoUpdateRequest;
import clinica_juridica.backend.dto.request.DocumentoCreateRequest;
import clinica_juridica.backend.dto.request.EncuentroCreateRequest;
import clinica_juridica.backend.dto.request.PruebaCreateRequest;
import clinica_juridica.backend.dto.response.AccionResponse;
import clinica_juridica.backend.dto.response.CasoDetalleResponse;
import clinica_juridica.backend.dto.response.CasoSummaryResponse;
import clinica_juridica.backend.dto.response.DocumentoResponse;
import clinica_juridica.backend.dto.response.EncuentroResponse;
import clinica_juridica.backend.dto.response.PruebaResponse;

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

    @Operation(summary = "Obtener casos (Resumen)", description = "Recupera una lista ligera de casos filtrada por estatus, usuario asignado o término (semestre). Roles requeridos: COORDINADOR, PROFESOR, ESTUDIANTE.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de casos recuperada exitosamente")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR', 'ESTUDIANTE')")
    public ResponseEntity<List<CasoSummaryResponse>> getAll(
            @Parameter(description = "Estatus del caso para filtrar") @RequestParam(required = false) String estatus,
            @Parameter(description = "Nombre de usuario para filtrar sus casos asignados") @RequestParam(required = false) String username,
            @Parameter(description = "Término o semestre (ej: 2024-15)") @RequestParam(required = false) String termino) {

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

    @Operation(summary = "Crear nuevo caso", description = "Crea un nuevo caso legal en el sistema. Roles requeridos: COORDINADOR, PROFESOR, ESTUDIANTE.")
    @ApiResponse(responseCode = "200", description = "Caso creado exitosamente")
    @PostMapping
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR', 'ESTUDIANTE')")
    public ResponseEntity<CasoDetalleResponse> create(@RequestBody CasoCreateRequest request) {
        return ResponseEntity.ok(casoService.create(request));
    }

    @Operation(summary = "Actualizar caso", description = "Actualiza la información de un caso existente. Roles requeridos: COORDINADOR, PROFESOR, ESTUDIANTE.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caso actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR', 'ESTUDIANTE')")
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

    @Operation(summary = "Eliminar caso", description = "Elimina un caso del sistema por su ID. Roles requeridos: COORDINADOR.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caso eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID del caso a eliminar") @PathVariable String id) {
        if (casoService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        casoService.delete(id);
        return ResponseEntity.ok("Caso eliminado exitosamente");
    }

    @Operation(summary = "Actualizar estatus del caso", description = "Actualiza el estatus de un caso específico. Roles requeridos: COORDINADOR, PROFESOR.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatus actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, falta el campo 'estatus'")
    })
    @PatchMapping("/{id}/estatus")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR')")
    public ResponseEntity<String> updateEstatus(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @RequestBody CasoEstatusUpdateRequest request) {
        String nuevoEstatus = request.getEstatus();
        if (nuevoEstatus == null || nuevoEstatus.isBlank()) {
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

    @Operation(summary = "Agregar beneficiario", description = "Agrega un nuevo beneficiario a un caso existente.")
    @ApiResponse(responseCode = "200", description = "Beneficiario agregado exitosamente")
    @PostMapping("/{id}/beneficiarios")
    public void addBeneficiario(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @RequestBody BeneficiarioCreateRequest dto) {
        casoService.addBeneficiario(id, dto);
    }

    @Operation(summary = "Actualizar beneficiario", description = "Actualiza la relación (tipo y parentesco) de un beneficiario en un caso.")
    @ApiResponse(responseCode = "200", description = "Beneficiario actualizado exitosamente")
    @PatchMapping("/{id}/beneficiarios/{cedula}")
    public ResponseEntity<Void> updateBeneficiario(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @Parameter(description = "Cédula del beneficiario") @PathVariable String cedula,
            @RequestBody BeneficiarioUpdateRequest dto) {
        casoService.updateBeneficiario(id, cedula, dto);
        return ResponseEntity.ok().build();
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

    @Operation(summary = "Actualizar acción", description = "Actualiza los datos de una acción específica (Título, Descripción, Fecha Ejecución).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acción actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Caso o Acción no encontrada")
    })
    @PatchMapping("/{id}/acciones/{idAccion}")
    public ResponseEntity<Void> updateAccion(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @Parameter(description = "ID de la acción") @PathVariable Integer idAccion,
            @RequestBody AccionUpdateRequest request) {
        casoService.updateAccion(id, idAccion, request);
        return ResponseEntity.ok().build();
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

    @Operation(summary = "Asignar estudiante a caso", description = "Asigna un estudiante a un caso específico validando su registro en el término indicado. Roles requeridos: COORDINADOR, PROFESOR.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignación exitosa"),
            @ApiResponse(responseCode = "400", description = "Error de validación (Estudiante no encontrado/Término incorrecto)"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    })
    @PostMapping("/{id}/asignacion")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR')")
    public ResponseEntity<String> assignStudent(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @RequestBody CasoAsignacionRequest request) {
        try {
            casoService.assignStudent(id, request);
            return ResponseEntity.ok("Estudiante asignado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Asignar supervisor a caso", description = "Asigna un profesor supervisor a un caso validando su registro. Roles requeridos: COORDINADOR, PROFESOR.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignación de supervisión exitosa"),
            @ApiResponse(responseCode = "400", description = "Error de validación"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado")
    })
    @PostMapping("/{id}/supervision")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR')")
    public ResponseEntity<String> assignSupervisor(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @RequestBody CasoSupervisionRequest request) {
        try {
            casoService.assignSupervisor(id, request);
            return ResponseEntity.ok("Supervisor asignado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @Operation(summary = "Eliminar documento", description = "Elimina un documento específico de un caso.")
    @ApiResponse(responseCode = "204", description = "Documento eliminado exitosamente")
    @DeleteMapping("/{id}/documentos/{idDocumento}")
    public ResponseEntity<Void> deleteDocumento(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @Parameter(description = "ID del documento a eliminar") @PathVariable Integer idDocumento) {
        casoService.deleteDocumento(id, idDocumento);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Eliminar prueba", description = "Elimina una prueba específica de un caso.")
    @ApiResponse(responseCode = "204", description = "Prueba eliminada exitosamente")
    @DeleteMapping("/{id}/pruebas/{idPrueba}")
    public ResponseEntity<Void> deletePrueba(
            @Parameter(description = "ID del caso") @PathVariable String id,
            @Parameter(description = "ID de la prueba a eliminar") @PathVariable Integer idPrueba) {
        casoService.deletePrueba(id, idPrueba);
        return ResponseEntity.noContent().build();
    }
}
