package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Estudiante;
import clinica_juridica.backend.models.Usuario;
import clinica_juridica.backend.models.enums.TipoUsuario;
import clinica_juridica.backend.repository.EstudianteRepository;
import clinica_juridica.backend.repository.UsuarioRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ImportService {

    private final UsuarioRepository usuarioRepository;
    private final EstudianteRepository estudianteRepository;

    public ImportService(UsuarioRepository usuarioRepository, EstudianteRepository estudianteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Transactional
    public Map<String, Object> importEstudiantes(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename != null && (filename.endsWith(".xlsx") || filename.endsWith(".xls"))) {
            return importEstudiantesExcel(file);
        } else {
            return importEstudiantesCSV(file);
        }
    }

    private Map<String, Object> importEstudiantesExcel(MultipartFile file) throws IOException {
        Map<String, Object> report = new HashMap<>();
        int created = 0;
        int updated = 0;
        int failed = 0;
        List<String> errors = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            // Assume header is at row 0
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IOException("Excel file is empty or missing headers");
            }

            Map<String, Integer> headerMap = new HashMap<>();
            for (Cell cell : headerRow) {
                headerMap.put(dataFormatter.formatCellValue(cell).trim().toUpperCase(), cell.getColumnIndex());
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                try {
                    String cedula = getCellValue(row, headerMap, "CEDULA", dataFormatter);
                    String nombre = getCellValue(row, headerMap, "NOMBRE_ESTUDIANTE", dataFormatter);
                    String email = getCellValue(row, headerMap, "ESTU_EMAIL_ADDRESS", dataFormatter);

                    if (email == null || email.isBlank() || cedula == null || cedula.isBlank()) {
                        failed++;
                        errors.add("Row " + (i + 1) + ": Missing Email or Cedula");
                        continue;
                    }

                    ProcessResult result = processUserAndStudent(cedula, nombre, email, row, headerMap, dataFormatter);
                    if (result == ProcessResult.CREATED)
                        created++;
                    else if (result == ProcessResult.UPDATED)
                        updated++;

                } catch (Exception e) {
                    failed++;
                    errors.add("Row " + (i + 1) + ": " + e.getMessage());
                }
            }
        }

        report.put("created", created);
        report.put("updated", updated);
        report.put("failed", failed);
        report.put("errors", errors);
        return report;
    }

    private String getCellValue(Row row, Map<String, Integer> headerMap, String columnName, DataFormatter formatter) {
        Integer index = headerMap.get(columnName);
        if (index == null)
            return null;
        Cell cell = row.getCell(index);
        return formatter.formatCellValue(cell);
    }

    private Map<String, Object> importEstudiantesCSV(MultipartFile file) throws IOException {
        Map<String, Object> report = new HashMap<>();
        int created = 0;
        int updated = 0;
        int failed = 0;
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(reader,
                        CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true)
                                .setTrim(true).build())) {

            for (CSVRecord csvRecord : csvParser) {
                try {
                    String cedula = csvRecord.get("CEDULA");
                    String nombre = csvRecord.get("NOMBRE_ESTUDIANTE");
                    String email = csvRecord.get("ESTU_EMAIL_ADDRESS");

                    if (email == null || email.isBlank() || cedula == null || cedula.isBlank()) {
                        failed++;
                        errors.add("Row " + csvRecord.getRecordNumber() + ": Missing Email or Cedula");
                        continue;
                    }

                    // Adapt CSVRecord to a common interface or just extract values here
                    // Since logic is shared, it's better to duplicate small logic or adapter
                    // pattern.
                    // For simplicity in this edit, I'll extract vars.

                    String termino = csvRecord.isMapped("YEAR_TERM") ? csvRecord.get("YEAR_TERM") : "UNKNOWN";
                    String nrcStr = csvRecord.isMapped("CRN") ? csvRecord.get("CRN") : "0";
                    String modalidad = csvRecord.isMapped("MODALIDAD") ? csvRecord.get("MODALIDAD") : "";
                    String courseStatus = csvRecord.isMapped("COURSE_STATUS_INSC") ? csvRecord.get("COURSE_STATUS_INSC")
                            : "";

                    ProcessResult result = processCommon(cedula, nombre, email, termino, nrcStr, modalidad,
                            courseStatus);
                    if (result == ProcessResult.CREATED)
                        created++;
                    else if (result == ProcessResult.UPDATED)
                        updated++;

                } catch (Exception e) {
                    failed++;
                    errors.add("Row " + csvRecord.getRecordNumber() + ": " + e.getMessage());
                }
            }
        }

        report.put("created", created);
        report.put("updated", updated);
        report.put("failed", failed);
        report.put("errors", errors);

        return report;
    }

    private enum ProcessResult {
        CREATED, UPDATED
    }

    private ProcessResult processUserAndStudent(String cedula, String nombre, String email, Row row,
            Map<String, Integer> headerMap, DataFormatter formatter) {
        String termino = getCellValue(row, headerMap, "YEAR_TERM", formatter);
        if (termino == null)
            termino = "UNKNOWN";

        String nrcStr = getCellValue(row, headerMap, "CRN", formatter);
        if (nrcStr == null)
            nrcStr = "0";

        String modalidad = getCellValue(row, headerMap, "MODALIDAD", formatter);
        if (modalidad == null)
            modalidad = "";

        String courseStatus = getCellValue(row, headerMap, "COURSE_STATUS_INSC", formatter);
        if (courseStatus == null)
            courseStatus = "";

        return processCommon(cedula, nombre, email, termino, nrcStr, modalidad, courseStatus);
    }

    private ProcessResult processCommon(String cedula, String nombre, String email, String termino, String nrcStr,
            String modalidad, String courseStatus) {
        Optional<Usuario> existingUserByEmail = usuarioRepository.findByEmail(email);
        Optional<Usuario> existingUserByCedula = usuarioRepository.findByCedula(cedula);

        Usuario usuario;
        boolean isNewUser = false;

        if (existingUserByEmail.isPresent()) {
            usuario = existingUserByEmail.get();
        } else if (existingUserByCedula.isPresent()) {
            usuario = existingUserByCedula.get();
            usuario.setEmail(email);
        } else {
            isNewUser = true;
            usuario = new Usuario();
            usuario.setUsername(email.split("@")[0]);
            if (usuarioRepository.existsById(Objects.requireNonNull(usuario.getUsername()))) {
                usuario.setUsername(usuario.getUsername() + "_" + new Random().nextInt(1000));
            }
            usuario.setCedula(cedula);
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContrasena(null);
            usuario.setStatus("ACTIVO");
            usuario.setTipo(TipoUsuario.ESTUDIANTE.name());
        }

        usuarioRepository.save(Objects.requireNonNull(usuario));

        Integer nrc = 0;
        try {
            nrc = Integer.parseInt(nrcStr);
        } catch (NumberFormatException e) {
            // ignore
        }

        String tipoEstudiante = modalidad + " - " + courseStatus;

        if (!estudianteRepository.existsByUsernameAndTermino(usuario.getUsername(), termino)) {
            Estudiante estudiante = new Estudiante(usuario.getUsername(), termino, tipoEstudiante, nrc);
            estudianteRepository.save(estudiante);
        }

        return isNewUser ? ProcessResult.CREATED : ProcessResult.UPDATED;
    }
}
