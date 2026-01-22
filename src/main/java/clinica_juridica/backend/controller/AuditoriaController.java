package clinica_juridica.backend.controller;

import clinica_juridica.backend.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/auditoria")
@Tag(name = "Auditoría", description = "Endpoints para la gestión y reportes de auditoría")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @Operation(summary = "Descargar Reporte de Auditoría (Excel)", description = "Genera y descarga la bitácora de auditoría del sistema filtrada por fecha.")
    @GetMapping("/reporte")
    public ResponseEntity<byte[]> descargarReporteAuditoria(
            @RequestParam @Parameter(description = "Fecha de inicio (YYYY-MM-DD)") String inicio,
            @RequestParam @Parameter(description = "Fecha de fin (YYYY-MM-DD)") String fin)
            throws IOException {
        LocalDate fechaInicio = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);

        byte[] content = auditoriaService.generarReporteAuditoria(fechaInicio, fechaFin);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "form-data; name=\"attachment\"; filename=\"auditoria_sistema.xlsx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }
}
