package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.request.DatosEncuestaRequest;
import clinica_juridica.backend.dto.response.DatosEncuestaResponse;
import clinica_juridica.backend.service.EncuestaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitantes/{cedula}/encuesta")
@Tag(name = "Encuesta Socioeconómica", description = "Endpoints para gestionar la encuesta socioeconómica de solicitantes")
public class EncuestaController {

    private final EncuestaService encuestaService;

    public EncuestaController(EncuestaService encuestaService) {
        this.encuestaService = encuestaService;
    }

    @Operation(summary = "Obtener Encuesta", description = "Retorna los datos actuales de familia, vivienda y características del solicitante")
    @GetMapping
    public ResponseEntity<DatosEncuestaResponse> getEncuesta(@PathVariable String cedula) {
        return ResponseEntity.ok(encuestaService.getEncuesta(cedula));
    }

    @Operation(summary = "Guardar Encuesta", description = "Guarda o actualiza los datos de familia, vivienda y características")
    @PostMapping
    public ResponseEntity<String> saveEncuesta(
            @PathVariable String cedula,
            @RequestBody DatosEncuestaRequest request) {
        encuestaService.saveEncuesta(cedula, request);
        return ResponseEntity.ok("Encuesta guardada correctamente");
    }
}
