package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.AmbitoLegalResponse;
import clinica_juridica.backend.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Parameter;

import clinica_juridica.backend.dto.request.CreateCatalogoRequest;
import clinica_juridica.backend.dto.request.CreateViviendaRequest;
import clinica_juridica.backend.dto.response.CondicionActividadResponse;
import clinica_juridica.backend.dto.response.CondicionLaboralResponse;
import clinica_juridica.backend.dto.response.NivelEducativoResponse;
import clinica_juridica.backend.dto.response.TipoViviendaResponse;
import clinica_juridica.backend.dto.response.TribunalResponse;
import clinica_juridica.backend.dto.response.EstadoResponse;
import clinica_juridica.backend.dto.response.MunicipioResponse;
import clinica_juridica.backend.dto.response.ParroquiaResponse;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@Tag(name = "Catálogos", description = "Endpoints para obtener listas normalizadas (Ambitos, Categorías, Subcategorías, Ámbitos)")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @Operation(summary = "Obtener Árbol de Ámbitos Legales", description = "Retorna la jerarquía completa (Materia -> Categoría -> Subcategoría -> Ámbito) para selectores en cascada.")
    @GetMapping("/ambitos-legales")
    public ResponseEntity<List<AmbitoLegalResponse>> getAmbitosLegalesTree() {
        return ResponseEntity.ok(catalogoService.getAmbitosLegalesTree());
    }

    @Operation(summary = "Catálogo de Viviendas", description = "Retorna la jerarquía de tipos y categorías. Filtro opcional por estatus.")
    @GetMapping("/viviendas")
    public ResponseEntity<List<TipoViviendaResponse>> getViviendas(
            @Parameter(description = "Estatus para filtrar (ACTIVO/INACTIVO)") @RequestParam(required = false) String estatus) {
        return ResponseEntity.ok(catalogoService.getViviendas(estatus));
    }

    @Operation(summary = "Listar Tribunales", description = "Retorna la lista de todos los tribunales disponibles.")
    @GetMapping("/tribunales")
    public ResponseEntity<List<TribunalResponse>> getTribunales() {
        return ResponseEntity.ok(catalogoService.getTribunales());
    }

    @Operation(summary = "Catálogo de Niveles Educativos", description = "Retorna la lista de niveles educativos. Filtro opcional por estatus.")
    @GetMapping("/niveles-educativos")
    public ResponseEntity<List<NivelEducativoResponse>> getNivelesEducativos(
            @Parameter(description = "Estatus para filtrar (ACTIVO/INACTIVO)") @RequestParam(required = false) String estatus) {
        return ResponseEntity.ok(catalogoService.getNivelesEducativos(estatus));
    }

    @Operation(summary = "Catálogo de Condición Laboral", description = "Retorna la lista de condiciones laborales. Puede filtrar por estatus.")
    @GetMapping("/condiciones-laborales")
    public ResponseEntity<List<CondicionLaboralResponse>> getCondicionesLaborales(
            @Parameter(description = "Estatus para filtrar (ACTIVO/INACTIVO)") @RequestParam(required = false) String estatus) {
        return ResponseEntity.ok(catalogoService.getCondicionesLaborales(estatus));
    }

    @Operation(summary = "Catálogo de Situación Laboral (Condición Actividad)", description = "Retorna la lista de condiciones de actividad. Puede filtrar por estatus.")
    @GetMapping("/condiciones-actividad")
    public ResponseEntity<List<CondicionActividadResponse>> getCondicionesActividad(
            @Parameter(description = "Estatus para filtrar (ACTIVO/INACTIVO)") @RequestParam(required = false) String estatus) {
        return ResponseEntity.ok(catalogoService.getCondicionesActividad(estatus));
    }

    @Operation(summary = "Catálogo de Estados", description = "Retorna la lista de estados.")
    @GetMapping("/estados")
    public ResponseEntity<List<EstadoResponse>> getEstados() {
        return ResponseEntity.ok(catalogoService.getEstados());
    }

    @Operation(summary = "Catálogo de Municipios", description = "Retorna la lista de municipios por estado.")
    @GetMapping("/municipios")
    public ResponseEntity<List<MunicipioResponse>> getMunicipios(
            @Parameter(description = "ID del Estado") @RequestParam Integer idEstado) {
        return ResponseEntity.ok(catalogoService.getMunicipios(idEstado));
    }

    @Operation(summary = "Catálogo de Parroquias", description = "Retorna la lista de parroquias por municipio.")
    @GetMapping("/parroquias")
    public ResponseEntity<List<ParroquiaResponse>> getParroquias(
            @Parameter(description = "ID del Municipio") @RequestParam Integer idMunicipio) {
        return ResponseEntity.ok(catalogoService.getParroquias(idMunicipio));
    }

    @Operation(summary = "Catálogo Completo de Municipios", description = "Retorna todos los municipios sin filtrar.")
    @GetMapping("/municipios/all")
    public ResponseEntity<List<MunicipioResponse>> getAllMunicipios() {
        return ResponseEntity.ok(catalogoService.getAllMunicipios());
    }

    @Operation(summary = "Catálogo Completo de Parroquias", description = "Retorna todas las parroquias sin filtrar.")
    @GetMapping("/parroquias/all")
    public ResponseEntity<List<ParroquiaResponse>> getAllParroquias() {
        return ResponseEntity.ok(catalogoService.getAllParroquias());
    }

    @Operation(summary = "Catálogo de Estados Civiles", description = "Retorna la lista de estados civiles.")
    @GetMapping("/estados-civiles")
    public ResponseEntity<List<clinica_juridica.backend.dto.response.EstadoCivilResponse>> getEstadosCiviles() {
        return ResponseEntity.ok(catalogoService.getEstadosCiviles());
    }

    @Operation(summary = "Actualizar Estatus de Nivel Educativo", description = "Actualiza el estatus (ACTIVO/INACTIVO) de un nivel educativo.")
    @PatchMapping("/niveles-educativos/{id}/estatus")
    public ResponseEntity<String> updateNivelEducativoStatus(
            @PathVariable Integer id,
            @RequestParam String estatus) {
        catalogoService.updateNivelEducativoStatus(id, estatus);
        return ResponseEntity.ok("Estatus actualizado correctamente");
    }

    @Operation(summary = "Actualizar Estatus de Condición Laboral", description = "Actualiza el estatus (ACTIVO/INACTIVO) de una condición laboral.")
    @PatchMapping("/condiciones-laborales/{id}/estatus")
    public ResponseEntity<String> updateCondicionLaboralStatus(
            @PathVariable Integer id,
            @RequestParam String estatus) {
        catalogoService.updateCondicionLaboralStatus(id, estatus);
        return ResponseEntity.ok("Estatus actualizado correctamente");
    }

    @Operation(summary = "Actualizar Estatus de Condición Actividad", description = "Actualiza el estatus (ACTIVO/INACTIVO) de una condición de actividad.")
    @PatchMapping("/condiciones-actividad/{id}/estatus")
    public ResponseEntity<String> updateCondicionActividadStatus(
            @PathVariable Integer id,
            @RequestParam String estatus) {
        catalogoService.updateCondicionActividadStatus(id, estatus);
        return ResponseEntity.ok("Estatus actualizado correctamente");
    }

    @Operation(summary = "Actualizar Estatus de Categoría de Vivienda", description = "Actualiza el estatus (ACTIVO/INACTIVO) de una categoría de vivienda.")
    @PatchMapping("/viviendas/{idTipo}/{idCat}/estatus")
    public ResponseEntity<String> updateCategoriaViviendaStatus(
            @PathVariable Integer idTipo,
            @PathVariable Integer idCat,
            @RequestParam String estatus) {
        catalogoService.updateCategoriaViviendaStatus(idTipo, idCat, estatus);
        return ResponseEntity.ok("Estatus actualizado correctamente");
    }

    @Operation(summary = "Crear Nivel Educativo", description = "Crea un nuevo nivel educativo.")
    @PostMapping("/niveles-educativos")
    public ResponseEntity<String> createNivelEducativo(
            @RequestBody CreateCatalogoRequest request) {
        catalogoService.createNivelEducativo(request.getNombre());
        return ResponseEntity.ok("Nivel Educativo creado correctamente");
    }

    @Operation(summary = "Crear Condición Laboral", description = "Crea una nueva condición laboral.")
    @PostMapping("/condiciones-laborales")
    public ResponseEntity<String> createCondicionLaboral(
            @RequestBody CreateCatalogoRequest request) {
        catalogoService.createCondicionLaboral(request.getNombre());
        return ResponseEntity.ok("Condición Laboral creada correctamente");
    }

    @Operation(summary = "Crear Condición Actividad", description = "Crea una nueva condición de actividad.")
    @PostMapping("/condiciones-actividad")
    public ResponseEntity<String> createCondicionActividad(
            @RequestBody CreateCatalogoRequest request) {
        catalogoService.createCondicionActividad(request.getNombre());
        return ResponseEntity.ok("Condición Actividad creada correctamente");
    }

    @Operation(summary = "Crear Tipo de Vivienda", description = "Crea una nueva categoría padre (Tipo) de vivienda.")
    @PostMapping("/viviendas/tipos")
    public ResponseEntity<String> createTipoVivienda(
            @RequestBody CreateCatalogoRequest request) {
        catalogoService.createTipoVivienda(request.getNombre());
        return ResponseEntity.ok("Tipo de vivienda creado correctamente");
    }

    @Operation(summary = "Crear Categoría de Vivienda", description = "Crea una nueva característica de vivienda asociada a un Tipo existente.")
    @PostMapping("/viviendas")
    public ResponseEntity<String> createVivienda(
            @RequestBody CreateViviendaRequest request) {
        catalogoService.createVivienda(request.getIdTipo(), request.getDescripcion());
        return ResponseEntity.ok("Categoría de vivienda creada correctamente");
    }
}
