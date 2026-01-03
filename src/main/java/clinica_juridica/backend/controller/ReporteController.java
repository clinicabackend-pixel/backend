package clinica_juridica.backend.controller;

import clinica_juridica.backend.service.ReporteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "API para la generación de reportes en Excel")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Operation(summary = "Descargar Reporte General", description = "Genera y descarga un reporte Excel con el listado general de casos y su estatus.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al generar el reporte")
    })
    @GetMapping("/general")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR', 'ESTUDIANTE')")
    public ResponseEntity<byte[]> getReporteGeneral() {
        try {
            byte[] content = reporteService.generarReporteGeneral();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(
                    MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "reporte_general.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(content, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Descargar Reporte de Caso", description = "Genera y descarga un reporte Excel detallado de un caso específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al generar el reporte")
    })
    @GetMapping("/caso/{id}")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'PROFESOR', 'ESTUDIANTE')")
    public ResponseEntity<byte[]> getReporteCaso(
            @Parameter(description = "ID del caso") @PathVariable String id) {
        try {
            byte[] content = reporteService.generarReporteCaso(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(
                    MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "reporte_caso_" + id + ".xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(content, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
