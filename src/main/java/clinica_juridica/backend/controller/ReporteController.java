package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.DashboardStatsDto;
import clinica_juridica.backend.service.PdfService;
import clinica_juridica.backend.service.ReporteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final PdfService pdfService;
    private final ReporteService reporteService;

    public ReporteController(PdfService pdfService, ReporteService reporteService) {
        this.pdfService = pdfService;
        this.reporteService = reporteService;
    }

    @GetMapping("/ficha/{cedula}/pdf")
    public ResponseEntity<byte[]> descargarFichaPdf(@PathVariable @org.springframework.lang.NonNull String cedula) {
        byte[] pdfBytes = pdfService.generarFichaPdf(cedula);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ficha_" + cedula + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/general")
    public ResponseEntity<byte[]> descargarReporteGeneral() throws IOException {
        byte[] content = reporteService.generarReporteGeneral();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "form-data; name=\"attachment\"; filename=\"reporte_general.xlsx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }

    @GetMapping("/solicitante/{cedula}")
    public ResponseEntity<byte[]> descargarFichaSolicitante(@PathVariable String cedula) throws IOException {
        byte[] content = reporteService.generarFichaSolicitante(cedula);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "form-data; name=\"attachment\"; filename=\"ficha_solicitante_" + cedula + ".xlsx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }

    @GetMapping("/historial-casos")
    public ResponseEntity<byte[]> descargarHistorialCasos(
            @RequestParam(required = false) String cedula,
            @RequestParam String inicio,
            @RequestParam String fin,
            @RequestParam(required = false) String usuario) throws IOException {
        LocalDate fechaInicio = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);

        byte[] content;
        String filename;

        if (cedula != null && !cedula.isEmpty()) {
            content = reporteService.generarHistorialCasos(cedula, fechaInicio, fechaFin);
            filename = "historial_casos_" + cedula + ".xlsx";
        } else {
            content = reporteService.generarReporteHistorialGeneral(fechaInicio, fechaFin, usuario);
            filename = "historial_casos_general.xlsx";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "form-data; name=\"attachment\"; filename=\"" + filename + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }

    @GetMapping("/caso/{id}")
    public ResponseEntity<byte[]> descargarReporteCaso(@PathVariable String id) throws IOException {
        byte[] content = reporteService.generarReporteCaso(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "form-data; name=\"attachment\"; filename=\"reporte_caso_" + id + ".xlsx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }

    @GetMapping("/caso/{id}/pdf")
    public ResponseEntity<byte[]> descargarReporteCasoPdf(@PathVariable String id) {
        byte[] pdfBytes = pdfService.generarReporteCasoPdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=reporte_caso_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/por-estatus/{estatus}")
    public ResponseEntity<byte[]> descargarReportePorEstatus(@PathVariable String estatus) throws IOException {
        byte[] content = reporteService.generarReportePorEstatus(estatus);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "form-data; name=\"attachment\"; filename=\"casos_" + estatus + ".xlsx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }

    @GetMapping("/resumen")
    public ResponseEntity<byte[]> descargarResumenSemestral(
            @RequestParam String semestre,
            @RequestParam Integer tipoCaso) throws IOException {
        byte[] content = reporteService.generarInformeResumen(semestre, tipoCaso);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "form-data; name=\"attachment\"; filename=\"resumen_" + semestre + ".xlsx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getDashboardStats() {
        return ResponseEntity.ok(reporteService.getDashboardStats());
    }
}
