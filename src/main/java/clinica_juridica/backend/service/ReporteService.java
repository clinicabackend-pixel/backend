package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.projection.CasoAsignadoProjection;
import clinica_juridica.backend.dto.projection.CasoSupervisadoProjection;
import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.repository.CasoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private CasoRepository casoRepository;

    @Autowired
    private CasoService casoService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public byte[] generarReporteGeneral() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte General");
            createHeaderStyle(workbook); // Create style once

            // Headers
            String[] headers = {
                    "N° Caso", "Fecha Recepción", "Síntesis", "Estatus",
                    "Asignado a", "Término", "Cédula Solicitante", "Nombre Solicitante"
            };
            createHeaderRow(sheet, headers, workbook);

            // Data
            List<CasoSummaryResponse> casos = casoRepository.findAllByFilters(null, null, null);
            int rowNum = 1;
            for (CasoSummaryResponse caso : casos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(caso.getNumCaso());
                row.createCell(1).setCellValue(
                        caso.getFechaRecepcion() != null ? caso.getFechaRecepcion().format(DATE_FORMATTER) : "");
                row.createCell(2).setCellValue(caso.getSintesis());
                row.createCell(3).setCellValue(caso.getEstatus());
                row.createCell(4).setCellValue(caso.getUsername());
                row.createCell(5).setCellValue(caso.getTermino());
                row.createCell(6).setCellValue(caso.getCedula());
                row.createCell(7).setCellValue(caso.getNombreSolicitante());
            }

            // Autosize columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    public byte[] generarReporteCaso(String id) throws IOException {
        CasoDetalleResponse detalle = casoService.getCasoDetalle(id);

        try (Workbook workbook = new XSSFWorkbook()) {
            // Sheet 1: Resumen
            createResumenSheet(workbook, detalle.getCaso());

            // Sheet 2: Acciones
            createAccionesSheet(workbook, detalle.getAcciones());

            // Sheet 3: Encuentros
            createEncuentrosSheet(workbook, detalle.getEncuentros());

            // Sheet 4: Documentos
            createDocumentosSheet(workbook, detalle.getDocumentos());

            // Sheet 5: Pruebas
            createPruebasSheet(workbook, detalle.getPruebas());

            // Sheet 6: Beneficiarios
            createBeneficiariosSheet(workbook, detalle.getBeneficiarios());

            // Sheet 7: Equipo
            createEquipoSheet(workbook, detalle.getAsignados(), detalle.getSupervisores());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private void createResumenSheet(Workbook workbook, CasoResponse caso) {
        Sheet sheet = workbook.createSheet("Resumen del Caso");
        CellStyle headerStyle = createHeaderStyle(workbook);

        int rowIndex = 0;
        addRow(sheet, rowIndex++, "Número de Caso", caso.getNumCaso(), headerStyle);
        addRow(sheet, rowIndex++, "Estatus", caso.getEstatus(), headerStyle);
        addRow(sheet, rowIndex++, "Fecha Recepción",
                caso.getFechaRecepcion() != null ? caso.getFechaRecepcion().format(DATE_FORMATTER) : "", headerStyle);
        addRow(sheet, rowIndex++, "Solicitante CI", caso.getCedula(), headerStyle);
        addRow(sheet, rowIndex++, "Trámite", caso.getTramite(), headerStyle);
        addRow(sheet, rowIndex++, "Síntesis", caso.getSintesis(), headerStyle);
        addRow(sheet, rowIndex++, "Tribunal", caso.getNombreTribunal(), headerStyle);
        addRow(sheet, rowIndex++, "Código Tribunal", caso.getCodCasoTribunal(), headerStyle);

        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 10000);
    }

    private void addRow(Sheet sheet, int rowIndex, String label, String value, CellStyle style) {
        Row row = sheet.createRow(rowIndex);
        Cell cellLabel = row.createCell(0);
        cellLabel.setCellValue(label);
        cellLabel.setCellStyle(style);

        row.createCell(1).setCellValue(value);
    }

    private void createAccionesSheet(Workbook workbook, List<AccionResponse> acciones) {
        Sheet sheet = workbook.createSheet("Acciones");
        String[] headers = { "ID", "Título", "Descripción", "Fecha Ejecución", "Fecha Registro" };
        createHeaderRow(sheet, headers, workbook);

        int rowNum = 1;
        if (acciones != null) {
            for (AccionResponse accion : acciones) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(accion.getIdAccion());
                row.createCell(1).setCellValue(accion.getTitulo());
                row.createCell(2).setCellValue(accion.getDescripcion());
                row.createCell(3).setCellValue(
                        accion.getFechaEjecucion() != null ? accion.getFechaEjecucion().format(DATE_FORMATTER) : "");
                row.createCell(4).setCellValue(
                        accion.getFechaRegistro() != null ? accion.getFechaRegistro().format(DATE_FORMATTER) : "");
            }
        }
        autosizeColumns(sheet, headers.length);
    }

    private void createEncuentrosSheet(Workbook workbook, List<EncuentroResponse> encuentros) {
        Sheet sheet = workbook.createSheet("Encuentros");
        String[] headers = { "ID", "Observación", "Orientación", "Fecha Atención", "Fecha Próxima" };
        createHeaderRow(sheet, headers, workbook);

        int rowNum = 1;
        if (encuentros != null) {
            for (EncuentroResponse enc : encuentros) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(enc.getIdEncuentro());
                row.createCell(1).setCellValue(enc.getObservacion());
                row.createCell(2).setCellValue(enc.getOrientacion());
                row.createCell(3).setCellValue(
                        enc.getFechaAtencion() != null ? enc.getFechaAtencion().format(DATE_FORMATTER) : "");
                row.createCell(4).setCellValue(
                        enc.getFechaProxima() != null ? enc.getFechaProxima().format(DATE_FORMATTER) : "");
            }
        }
        autosizeColumns(sheet, headers.length);
    }

    private void createDocumentosSheet(Workbook workbook, List<DocumentoResponse> documentos) {
        Sheet sheet = workbook.createSheet("Documentos");
        String[] headers = { "ID", "Título", "Observación", "Folios", "Fecha Registro" };
        createHeaderRow(sheet, headers, workbook);

        int rowNum = 1;
        if (documentos != null) {
            for (DocumentoResponse doc : documentos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(doc.getIdDocumento());
                row.createCell(1).setCellValue(doc.getTitulo());
                row.createCell(2).setCellValue(doc.getObservacion());
                String folios = (doc.getFolioIni() != null ? doc.getFolioIni() : "") + " - "
                        + (doc.getFolioFin() != null ? doc.getFolioFin() : "");
                row.createCell(3).setCellValue(folios);
                row.createCell(4).setCellValue(
                        doc.getFechaRegistro() != null ? doc.getFechaRegistro().format(DATE_FORMATTER) : "");
            }
        }
        autosizeColumns(sheet, headers.length);
    }

    private void createPruebasSheet(Workbook workbook, List<PruebaResponse> pruebas) {
        Sheet sheet = workbook.createSheet("Pruebas");
        String[] headers = { "ID", "Título", "Documento", "Observación", "Fecha" };
        createHeaderRow(sheet, headers, workbook);

        int rowNum = 1;
        if (pruebas != null) {
            for (PruebaResponse p : pruebas) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getIdPrueba());
                row.createCell(1).setCellValue(p.getTitulo());
                row.createCell(2).setCellValue(p.getDocumento());
                row.createCell(3).setCellValue(p.getObservacion());
                row.createCell(4).setCellValue(p.getFecha() != null ? p.getFecha().format(DATE_FORMATTER) : "");
            }
        }
        autosizeColumns(sheet, headers.length);
    }

    private void createBeneficiariosSheet(Workbook workbook, List<BeneficiarioResponse> beneficiarios) {
        Sheet sheet = workbook.createSheet("Beneficiarios");
        String[] headers = { "Cédula", "Tipo", "Parentesco" };
        createHeaderRow(sheet, headers, workbook);

        int rowNum = 1;
        if (beneficiarios != null) {
            for (BeneficiarioResponse b : beneficiarios) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(b.getCedula());
                row.createCell(1).setCellValue(b.getTipoBeneficiario());
                row.createCell(2).setCellValue(b.getParentesco());
            }
        }
        autosizeColumns(sheet, headers.length);
    }

    private void createEquipoSheet(Workbook workbook, List<CasoAsignadoProjection> asignados,
            List<CasoSupervisadoProjection> supervisores) {
        Sheet sheet = workbook.createSheet("Equipo Asignado");

        // Estudiantes
        Row rowTitleEst = sheet.createRow(0);
        rowTitleEst.createCell(0).setCellValue("ESTUDIANTES ASIGNADOS");
        rowTitleEst.getCell(0).setCellStyle(createHeaderStyle(workbook));

        Row headerEst = sheet.createRow(1);
        headerEst.createCell(0).setCellValue("Nombre / Usuario");
        headerEst.createCell(1).setCellValue("Usuario");
        headerEst.createCell(2).setCellValue("Semestre");

        int rowNum = 2;
        if (asignados != null) {
            for (CasoAsignadoProjection a : asignados) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(a.getNombre());
                row.createCell(1).setCellValue(a.getUsername());
                row.createCell(2).setCellValue(a.getTermino());
            }
        }

        rowNum += 2;

        // Supervisores
        Row rowTitleSup = sheet.createRow(rowNum++);
        rowTitleSup.createCell(0).setCellValue("SUPERVISORES ASIGNADOS");
        rowTitleSup.getCell(0).setCellStyle(createHeaderStyle(workbook));

        Row headerSup = sheet.createRow(rowNum++);
        headerSup.createCell(0).setCellValue("Nombre");

        if (supervisores != null) {
            for (CasoSupervisadoProjection s : supervisores) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(s.getNombre()); // Fixed
            }
        }

        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 5000);
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private void createHeaderRow(Sheet sheet, String[] headers, Workbook workbook) {
        Row row = sheet.createRow(0);
        CellStyle style = createHeaderStyle(workbook);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void autosizeColumns(Sheet sheet, int count) {
        for (int i = 0; i < count; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
