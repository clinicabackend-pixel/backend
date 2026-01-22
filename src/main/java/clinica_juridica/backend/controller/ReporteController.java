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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Generación y descarga de reportes y fichas en PDF/Excel")
public class ReporteController {

        private final PdfService pdfService;
        private final ReporteService reporteService;

        public ReporteController(PdfService pdfService, ReporteService reporteService) {
                this.pdfService = pdfService;
                this.reporteService = reporteService;
        }

        @Operation(summary = "Descargar Ficha de Solicitante (PDF)", description = "Genera y descarga la ficha del solicitante en formato PDF.")
        @GetMapping("/ficha/{cedula}/pdf")
        public ResponseEntity<byte[]> descargarFichaPdf(
                        @PathVariable @Parameter(description = "Cédula del solicitante") @org.springframework.lang.NonNull String cedula) {
                byte[] pdfBytes = pdfService.generarFichaPdf(cedula);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ficha_" + cedula + ".pdf");

                return ResponseEntity.ok()
                                .headers(headers)
                                .body(pdfBytes);
        }

        @Operation(summary = "Descargar Reporte General (Excel)", description = "Genera y descarga un reporte general de casos en formato Excel.")
        @GetMapping("/general")
        public ResponseEntity<byte[]> descargarReporteGeneral() throws IOException {
                byte[] content = reporteService.generarReporteGeneral();

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE,
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                                "form-data; name=\"attachment\"; filename=\"reporte_general.xlsx\"");

                return ResponseEntity.ok()
                                .headers(headers)
                                .body(content);
        }

        @Operation(summary = "Descargar Reporte Socioeconómico Unificado (Excel)", description = "Genera y descarga un reporte consolidado con datos socioeconómicos de todos los solicitantes.")
        @GetMapping("/socioeconomico")
        public ResponseEntity<byte[]> descargarReporteSocioeconomico()
                        throws IOException {
                byte[] content = reporteService.generarReporteSocioeconomico();

                String filename = "reporte_socioeconomico.xlsx";

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE,
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                                "form-data; name=\"attachment\"; filename=\"" + filename + "\"");

                return ResponseEntity.ok()
                                .headers(headers)
                                .body(content);
        }

        @Operation(summary = "Obtener Datos para Gráficos Estadísticos", description = "Devuelve los datos (labels y valores) para un tipo de reporte específico.")
        @GetMapping("/chart-data")
        public ResponseEntity<clinica_juridica.backend.dto.stats.ReporteDataDto> getChartData(
                        @RequestParam clinica_juridica.backend.dto.stats.TipoReporte tipo) {
                return ResponseEntity.ok(reporteService.getReporteData(tipo));
        }

        @Operation(summary = "Descargar Ficha de Solicitante (Excel)", description = "Genera y descarga la ficha del solicitante en formato Excel.")
        @GetMapping("/solicitante/{cedula}")
        public ResponseEntity<byte[]> descargarFichaSolicitante(
                        @PathVariable @Parameter(description = "Cédula del solicitante") String cedula)
                        throws IOException {
                byte[] content = reporteService.generarFichaSolicitante(cedula);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE,
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                                "form-data; name=\"attachment\"; filename=\"ficha_solicitante_" + cedula + ".xlsx\"");

                return ResponseEntity.ok()
                                .headers(headers)
                                .body(content);
        }

        @Operation(summary = "Descargar Historial de Casos (Excel)", description = "Genera un reporte de historial de casos, filtrado por fecha y opcionalmente por cédula o usuario.")
        @GetMapping("/historial-casos")
        public ResponseEntity<byte[]> descargarHistorialCasos(
                        @RequestParam(required = false) @Parameter(description = "Cédula del solicitante (opcional)") String cedula,
                        @RequestParam @Parameter(description = "Fecha de inicio (YYYY-MM-DD)") String inicio,
                        @RequestParam @Parameter(description = "Fecha de fin (YYYY-MM-DD)") String fin,
                        @RequestParam(required = false) @Parameter(description = "Usuario (opcional)") String usuario)
                        throws IOException {
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
                headers.add(HttpHeaders.CONTENT_TYPE,
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                                "form-data; name=\"attachment\"; filename=\"" + filename + "\"");

                return ResponseEntity.ok()
                                .headers(headers)
                                .body(content);
        }

        @Operation(summary = "Descargar Reporte de Caso (Excel)", description = "Genera y descarga el reporte detallado de un caso específico en Excel.")
        @GetMapping("/caso/{id}")
        public ResponseEntity<byte[]> descargarReporteCaso(
                        @PathVariable @Parameter(description = "Número del caso") String id) throws IOException {
                byte[] content = reporteService.generarReporteCaso(id);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE,
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                                "form-data; name=\"attachment\"; filename=\"reporte_caso_" + id + ".xlsx\"");

                return ResponseEntity.ok()
                                .headers(headers)
                                .body(content);
        }

        @Operation(summary = "Descargar Reporte de Caso (PDF)", description = "Genera y descarga el reporte detallado de un caso específico en PDF.")
        @GetMapping("/caso/{id}/pdf")
        public ResponseEntity<byte[]> descargarReporteCasoPdf(
                        @PathVariable @Parameter(description = "Número del caso") String id) {
                byte[] pdfBytes = pdfService.generarReporteCasoPdf(id);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=reporte_caso_" + id + ".pdf");

                return ResponseEntity.ok()
                                .headers(headers)
                                .body(pdfBytes);
        }

        @Operation(summary = "Descargar Reporte por Estatus (Excel)", description = "Genera un reporte de casos filtrados por estatus en Excel.")
        @GetMapping("/por-estatus/{estatus}")
        public ResponseEntity<byte[]> descargarReportePorEstatus(
                        @PathVariable @Parameter(description = "Estatus del caso") String estatus) throws IOException {
                byte[] content = reporteService.generarReportePorEstatus(estatus);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE,
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                                "form-data; name=\"attachment\"; filename=\"casos_" + estatus + ".xlsx\"");

                return ResponseEntity.ok()
                                .headers(headers)
                                .body(content);
        }

        @Operation(summary = "Descargar Resumen Semestral (Excel)", description = "Genera un informe resumen semestral filtrado por tipo de caso (público/privado).")
        @GetMapping("/resumen")
        public ResponseEntity<byte[]> descargarResumenSemestral(
                        @RequestParam @Parameter(description = "Semestre (ej: 2024-1)") String semestre,
                        @RequestParam @Parameter(description = "Tipo de caso (1: Privado, 2: Público)") Integer tipoCaso)
                        throws IOException {
                byte[] content = reporteService.generarInformeResumen(semestre, tipoCaso);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE,
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                headers.add(HttpHeaders.CONTENT_DISPOSITION,
                                "form-data; name=\"attachment\"; filename=\"resumen_" + semestre + ".xlsx\"");

                return ResponseEntity.ok()
                                .headers(headers)
                                .body(content);
        }

        @Operation(summary = "Obtener Estadísticas del Dashboard", description = "Retorna estadísticas generales para el dashboard principal.")
        @GetMapping("/stats")
        public ResponseEntity<DashboardStatsDto> getDashboardStats() {
                return ResponseEntity.ok(reporteService.getDashboardStats());
        }

}
