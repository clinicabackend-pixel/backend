package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.AmbitoLegalResponse;
import clinica_juridica.backend.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "Catálogo de Viviendas", description = "Retorna la jerarquía de tipos y categorías de vivienda con estatus.")
    @GetMapping("/viviendas")
    public ResponseEntity<List<clinica_juridica.backend.dto.response.TipoViviendaResponse>> getViviendas() {
        return ResponseEntity.ok(catalogoService.getViviendas());
    }

    @Operation(summary = "Listar Tribunales", description = "Retorna la lista de todos los tribunales disponibles.")
    @GetMapping("/tribunales")
    public ResponseEntity<List<clinica_juridica.backend.dto.response.TribunalResponse>> getTribunales() {
        return ResponseEntity.ok(catalogoService.getTribunales());
    }
}
