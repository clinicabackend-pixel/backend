package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.EstudianteResponse;
import clinica_juridica.backend.service.EstudianteService;
import clinica_juridica.backend.service.ImportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/api/estudiantes")
@Tag(name = "Estudiantes", description = "Gestión de estudiantes y carga masiva")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final ImportService importService;

    public EstudianteController(EstudianteService estudianteService,
            ImportService importService) {
        this.estudianteService = estudianteService;
        this.importService = importService;
    }

    @Operation(summary = "Obtener lista de estudiantes", description = "Devuelve una lista de estudiantes, opcionalmente filtrada por estado activo o si tienen casos asignados.")
    @GetMapping
    public ResponseEntity<List<EstudianteResponse>> getEstudiantes(
            @RequestParam(required = false) @Parameter(description = "Filtrar por estudiantes activos (true/false)") Boolean activo,
            @RequestParam(required = false) @Parameter(description = "Filtrar por estudiantes con casos asignados (true/false)") Boolean conCasos) {
        return ResponseEntity.ok(estudianteService.getEstudiantes(activo, conCasos));
    }

    @Operation(summary = "Importar estudiantes masivamente", description = "Carga estudiantes desde un archivo Excel. Requiere rol de COORDINADOR.")
    @PostMapping("/importar")
    @PreAuthorize("hasRole('COORDINADOR')")
    public ResponseEntity<?> importarEstudiantes(
            @RequestParam("file") @Parameter(description = "Archivo Excel (.xlsx) con los datos de los estudiantes", content = @Content(mediaType = "multipart/form-data")) MultipartFile file) {
        try {
            Map<String, Object> report = importService.importEstudiantes(file);
            return ResponseEntity.ok(report);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }
}
