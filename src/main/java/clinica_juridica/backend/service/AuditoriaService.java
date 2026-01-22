package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Auditoria;
import clinica_juridica.backend.repository.AuditoriaRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public byte[] generarReporteAuditoria(LocalDate inicio, LocalDate fin) throws IOException {
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime finDateTime = fin.atTime(23, 59, 59);

        List<Auditoria> registros = auditoriaRepository.findByFechaEventoBetween(inicioDateTime, finDateTime);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Auditoría del Sistema");
            createHeaderStyle(workbook); // Ensure style is created (helper method below)

            String[] headers = {
                    "ID", "Fecha/Hora", "Usuario Aplicación", "Operación", "Tabla",
                    "Datos Anteriores (JSON)", "Datos Nuevos (JSON)"
            };
            createHeaderRow(sheet, headers, workbook);

            int rowIdx = 1;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            for (Auditoria aud : registros) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(aud.getIdAuditoria());
                row.createCell(1).setCellValue(
                        aud.getFechaEvento() != null ? aud.getFechaEvento().format(dateTimeFormatter) : "");
                row.createCell(2).setCellValue(aud.getUsernameAplicacion());
                row.createCell(3).setCellValue(aud.getOperacion());
                row.createCell(4).setCellValue(aud.getNombreTabla());

                // Truncate very long JSON strings to avoid Excel limits if necessary
                row.createCell(5).setCellValue(aud.getDatosAnteriores() != null ? aud.getDatosAnteriores() : "-");
                row.createCell(6).setCellValue(aud.getDatosNuevos() != null ? aud.getDatosNuevos() : "-");
            }

            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }
            sheet.setColumnWidth(5, 10000); // Fixed width for JSON columns
            sheet.setColumnWidth(6, 10000);

            workbook.write(out);
            return out.toByteArray();
        }
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
}
