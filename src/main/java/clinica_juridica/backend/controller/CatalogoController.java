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
import clinica_juridica.backend.dto.request.CentroRequest;
import clinica_juridica.backend.dto.response.CentroResponse;
import clinica_juridica.backend.dto.response.CondicionActividadResponse;
import clinica_juridica.backend.dto.response.CondicionLaboralResponse;
import clinica_juridica.backend.dto.response.NivelEducativoResponse;
import clinica_juridica.backend.dto.response.TipoViviendaResponse;
import clinica_juridica.backend.dto.response.TribunalResponse;
import clinica_juridica.backend.dto.response.EstadoResponse;
import clinica_juridica.backend.dto.response.MunicipioResponse;
import clinica_juridica.backend.dto.response.ParroquiaResponse;
import clinica_juridica.backend.dto.response.EstadoCivilResponse;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import clinica_juridica.backend.dto.response.SemestreResponse;
import java.util.List;
import org.springframework.lang.NonNull;

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
    public ResponseEntity<List<EstadoCivilResponse>> getEstadosCiviles() {
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
        catalogoService.updateCondicionLaboralStatus(id, estatus);
        return ResponseEntity.ok("Estatus actualizado correctamente");
    }

    @Operation(summary = "Actualizar Estatus de Estado Civil", description = "Actualiza el estatus (ACTIVO/INACTIVO) de un estado civil.")
    @PatchMapping("/estados-civiles/{id}/estatus")
    public ResponseEntity<String> updateEstadoCivilStatus(
            @PathVariable Integer id,
            @RequestParam String estatus) {
        catalogoService.updateEstadoCivilStatus(id, estatus);
        return ResponseEntity.ok("Estatus actualizado correctamente");
    }

    @Operation(summary = "Actualizar Estatus de Tribunal", description = "Actualiza el estatus (ACTIVO/INACTIVO) de un tribunal.")
    @PatchMapping("/tribunales/{id}/estatus")
    public ResponseEntity<String> updateTribunalStatus(
            @PathVariable Integer id,
            @RequestParam String estatus) {
        catalogoService.updateTribunalStatus(id, estatus);
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

    @Operation(summary = "Listar Centros", description = "Retorna la lista de todos los centros asociados.")
    @GetMapping("/centros")
    public ResponseEntity<List<CentroResponse>> getCentros() {
        return ResponseEntity.ok(catalogoService.getCentros());
    }

    @Operation(summary = "Crear Centro", description = "Crea un nuevo centro.")
    @PostMapping("/centros")
    public ResponseEntity<String> createCentro(@RequestBody CentroRequest request) {
        catalogoService.createCentro(request);
        return ResponseEntity.ok("Centro creado correctamente");
    }

    @Operation(summary = "Actualizar Centro", description = "Modifica los datos de un centro existente.")
    @PutMapping("/centros/{id}")
    public ResponseEntity<String> updateCentro(
            @PathVariable @NonNull Integer id,
            @RequestBody CentroRequest request) {
        catalogoService.updateCentro(id, request);
        return ResponseEntity.ok("Centro actualizado correctamente");
    }

    @Operation(summary = "Listar Semestres", description = "Retorna la lista de todos los semestres disponibles.")
    @GetMapping("/semestres")
    public ResponseEntity<List<SemestreResponse>> getSemestres() {
        return ResponseEntity.ok(catalogoService.getSemestres());
    }

    @Operation(summary = "Crear Estado Civil", description = "Crea un nuevo estado civil.")
    @PostMapping("/estados-civiles")
    public ResponseEntity<String> createEstadoCivil(
            @RequestBody CreateCatalogoRequest request) {
        catalogoService.createEstadoCivil(request.getNombre());
        return ResponseEntity.ok("Estado Civil creado correctamente");
    }

    @Operation(summary = "Crear Semestre", description = "Crea un nuevo semestre.")
    @PostMapping("/semestres")
    public ResponseEntity<String> createSemestre(
            @RequestBody clinica_juridica.backend.models.Semestre semestre) {
        catalogoService.createSemestre(semestre);
        return ResponseEntity.ok("Semestre creado correctamente");
    }

    @Operation(summary = "Crear Tribunal", description = "Crea un nuevo tribunal.")
    @PostMapping("/tribunales")
    public ResponseEntity<String> createTribunal(
            @RequestBody clinica_juridica.backend.models.Tribunal tribunal) {
        catalogoService.createTribunal(tribunal);
        return ResponseEntity.ok("Tribunal creado correctamente");
    }

    @Operation(summary = "Crear Materia", description = "Crea una nueva materia (Raíz Ámbito).")
    @PostMapping("/materias")
    public ResponseEntity<String> createMateria(
            @RequestBody CreateCatalogoRequest request) {
        catalogoService.createMateria(request.getNombre());
        return ResponseEntity.ok("Materia creada correctamente");
    }

    @Operation(summary = "Crear Categoría", description = "Crea una nueva categoría legal.")
    @PostMapping("/categorias")
    public ResponseEntity<String> createCategoria(
            @RequestBody clinica_juridica.backend.dto.request.CreateHierarchyRequest request) {
        catalogoService.createCategoria(request.nombre(), request.idMateria());
        return ResponseEntity.ok("Categoría creada correctamente");
    }

    @Operation(summary = "Crear Subcategoría", description = "Crea una nueva subcategoría legal.")
    @PostMapping("/subcategorias")
    public ResponseEntity<String> createSubcategoria(
            @RequestBody clinica_juridica.backend.dto.request.CreateHierarchyRequest request) {
        catalogoService.createSubcategoria(request.nombre(), request.idCategoria());
        return ResponseEntity.ok("Subcategoría creada correctamente");
    }

    @Operation(summary = "Crear Ámbito", description = "Crea un nuevo ámbito legal (hoja).")
    @PostMapping("/ambitos")
    public ResponseEntity<String> createAmbito(
            @RequestBody clinica_juridica.backend.dto.request.CreateHierarchyRequest request) {
        catalogoService.createAmbito(request.nombre(), request.idSubcategoria());
        return ResponseEntity.ok("Ámbito creado correctamente");
    }
}
