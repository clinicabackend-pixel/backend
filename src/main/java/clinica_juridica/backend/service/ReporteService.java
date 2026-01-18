package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.dto.projection.CasoAsignadoProjection;
import clinica_juridica.backend.dto.projection.CasoSupervisadoProjection;
import clinica_juridica.backend.repository.SolicitanteRepository;
import clinica_juridica.backend.repository.CasoRepository;
import clinica_juridica.backend.repository.FamiliaRepository;
import clinica_juridica.backend.repository.VistaReporteViviendaRepository;
import clinica_juridica.backend.repository.ParroquiaRepository;
import clinica_juridica.backend.repository.MunicipioRepository;
import clinica_juridica.backend.repository.EstadoRepository;
import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.models.Solicitante;
import clinica_juridica.backend.models.Familia;
import clinica_juridica.backend.models.VistaReporteVivienda;
import clinica_juridica.backend.models.Parroquia;
import clinica_juridica.backend.models.Municipio;
import clinica_juridica.backend.models.Estado;
import clinica_juridica.backend.models.AmbitoLegal;
import clinica_juridica.backend.models.SubcategoriaAmbitoLegal;
import clinica_juridica.backend.models.CategoriaAmbitoLegal;
import clinica_juridica.backend.models.MateriaAmbitoLegal;
import clinica_juridica.backend.repository.AmbitoLegalRepository;
import clinica_juridica.backend.repository.SubcategoriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.CategoriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.MateriaAmbitoLegalRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@SuppressWarnings("null")
public class ReporteService {

    private final CasoRepository casoRepository;
    private final CasoService casoService;
    private final SolicitanteRepository solicitanteRepository;
    private final FamiliaRepository familiaRepository;
    private final VistaReporteViviendaRepository vistaReporteViviendaRepository;
    private final ParroquiaRepository parroquiaRepository;
    private final MunicipioRepository municipioRepository;
    private final EstadoRepository estadoRepository;
    private final AmbitoLegalRepository ambitoLegalRepository;
    private final SubcategoriaAmbitoLegalRepository subcategoriaAmbitoLegalRepository;
    private final CategoriaAmbitoLegalRepository categoriaAmbitoLegalRepository;
    private final MateriaAmbitoLegalRepository materiaAmbitoLegalRepository;

    public ReporteService(CasoRepository casoRepository, CasoService casoService,
            SolicitanteRepository solicitanteRepository, FamiliaRepository familiaRepository,
            VistaReporteViviendaRepository vistaReporteViviendaRepository, ParroquiaRepository parroquiaRepository,
            MunicipioRepository municipioRepository, EstadoRepository estadoRepository,
            AmbitoLegalRepository ambitoLegalRepository,
            SubcategoriaAmbitoLegalRepository subcategoriaAmbitoLegalRepository,
            CategoriaAmbitoLegalRepository categoriaAmbitoLegalRepository,
            MateriaAmbitoLegalRepository materiaAmbitoLegalRepository) {
        this.casoRepository = casoRepository;
        this.casoService = casoService;
        this.solicitanteRepository = solicitanteRepository;
        this.familiaRepository = familiaRepository;
        this.vistaReporteViviendaRepository = vistaReporteViviendaRepository;
        this.parroquiaRepository = parroquiaRepository;
        this.municipioRepository = municipioRepository;
        this.estadoRepository = estadoRepository;
        this.ambitoLegalRepository = ambitoLegalRepository;
        this.subcategoriaAmbitoLegalRepository = subcategoriaAmbitoLegalRepository;
        this.categoriaAmbitoLegalRepository = categoriaAmbitoLegalRepository;
        this.materiaAmbitoLegalRepository = materiaAmbitoLegalRepository;
    }

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

    public byte[] generarFichaSolicitante(String cedula) throws IOException {
        Solicitante solicitante = solicitanteRepository.findById(cedula)
                .orElseThrow(() -> new RuntimeException("Solicitante no encontrado"));

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ficha Solicitante");
            CellStyle headerStyle = createHeaderStyle(workbook);

            int rowIdx = 0;
            addRow(sheet, rowIdx++, "Cédula", solicitante.getCedula(), headerStyle);
            addRow(sheet, rowIdx++, "Nombre", solicitante.getNombre(), headerStyle);
            addRow(sheet, rowIdx++, "Nacionalidad", solicitante.getNacionalidad(), headerStyle);
            addRow(sheet, rowIdx++, "Sexo", solicitante.getSexo(), headerStyle);
            addRow(sheet, rowIdx++, "Email", solicitante.getEmail(), headerStyle);
            addRow(sheet, rowIdx++, "Teléfono Celular", solicitante.getTelfCelular(), headerStyle);
            addRow(sheet, rowIdx++, "Teléfono Casa", solicitante.getTelfCasa(), headerStyle);
            addRow(sheet, rowIdx++, "Fecha Nacimiento",
                    solicitante.getFNacimiento() != null ? solicitante.getFNacimiento().toString() : "N/A",
                    headerStyle);
            addRow(sheet, rowIdx++, "Edad", solicitante.getEdad() != null ? solicitante.getEdad().toString() : "N/A",
                    headerStyle);

            // Dirección
            if (solicitante.getIdParroquia() != null) {
                String direccion = "Desconocida";
                Parroquia p = parroquiaRepository.findById(solicitante.getIdParroquia()).orElse(null);
                if (p != null) {
                    Municipio m = municipioRepository.findById(p.getIdMunicipio()).orElse(null);
                    String estadoStr = "";
                    if (m != null) {
                        Estado e = estadoRepository.findById(m.getIdEstado()).orElse(null);
                        if (e != null) {
                            estadoStr = ", Edo. " + e.getNombreEstado();
                        }
                    }
                    direccion = p.getNombreParroquia() + (m != null ? ", Mun. " + m.getNombreMunicipio() : "")
                            + estadoStr;
                }
                addRow(sheet, rowIdx++, "Dirección", direccion, headerStyle);
            }

            // --- SECCIÓN: ENCUESTA SOCIOECONÓMICA ---
            rowIdx++; // Spacer
            Row titleRow = sheet.createRow(rowIdx++);
            titleRow.createCell(0).setCellValue("ENCUESTA SOCIOECONÓMICA");
            titleRow.getCell(0).setCellStyle(headerStyle);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(rowIdx - 1, rowIdx - 1, 0, 1));

            // 1. Datos Familiares
            Familia familia = familiaRepository.findById(cedula).orElse(null);
            if (familia != null) {
                rowIdx++;
                Row famHeader = sheet.createRow(rowIdx++);
                famHeader.createCell(0).setCellValue("Datos Familiares");
                famHeader.getCell(0).setCellStyle(headerStyle);

                addRow(sheet, rowIdx++, "Cant. Personas",
                        familia.getCantPersonas() != null ? familia.getCantPersonas().toString() : "", null);
                addRow(sheet, rowIdx++, "Ingreso Mensual",
                        familia.getIngresoMes() != null ? familia.getIngresoMes().toString() : "", null);
                addRow(sheet, rowIdx++, "Es Jefe de Familia",
                        Boolean.TRUE.equals(familia.getJefeFamilia()) ? "Sí" : "No", null);
                addRow(sheet, rowIdx++, "Cant. Niños",
                        familia.getCantNinos() != null ? familia.getCantNinos().toString() : "", null);
                addRow(sheet, rowIdx++, "Cant. Trabajan",
                        familia.getCantTrabaja() != null ? familia.getCantTrabaja().toString() : "", null);
            }

            // 2. Vivienda
            VistaReporteVivienda vivienda = vistaReporteViviendaRepository
                    .findById(cedula).orElse(null);
            if (vivienda != null) {
                rowIdx++;
                Row vivHeader = sheet.createRow(rowIdx++);
                vivHeader.createCell(0).setCellValue("Vivienda");
                vivHeader.getCell(0).setCellStyle(headerStyle);

                addRow(sheet, rowIdx++, "Tipo Vivienda", vivienda.getTipoVivienda(), null);
                addRow(sheet, rowIdx++, "Habitaciones",
                        vivienda.getCantHabit() != null ? vivienda.getCantHabit().toString() : "", null);
                addRow(sheet, rowIdx++, "Baños",
                        vivienda.getCantBanos() != null ? vivienda.getCantBanos().toString() : "", null);
                addRow(sheet, rowIdx++, "Piso", vivienda.getMaterialPiso(), null);
                addRow(sheet, rowIdx++, "Paredes", vivienda.getMaterialParedes(), null);
                addRow(sheet, rowIdx++, "Techo", vivienda.getMaterialTecho(), null);
                addRow(sheet, rowIdx++, "Servicio Agua", vivienda.getServicioAgua(), null);
            }

            // --- SECCIÓN: CASOS COMO SOLICITANTE ---
            rowIdx++;
            Row solHeader = sheet.createRow(rowIdx++);
            solHeader.createCell(0).setCellValue("Casos como Solicitante");
            solHeader.getCell(0).setCellStyle(headerStyle);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(rowIdx - 1, rowIdx - 1, 0, 1));

            List<CasoSummaryResponse> casosSolicitante = casoRepository.findCasosBySolicitanteCedula(cedula);
            if (!casosSolicitante.isEmpty()) {
                String[] caseHeaders = { "N° Caso", "Fecha", "Estatus", "Materia", "Síntesis" };
                Row headerRow = sheet.createRow(rowIdx++);
                for (int i = 0; i < caseHeaders.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(caseHeaders[i]);
                    cell.setCellStyle(headerStyle); // Using same header style
                }

                for (CasoSummaryResponse c : casosSolicitante) {
                    Row r = sheet.createRow(rowIdx++);
                    r.createCell(0).setCellValue(c.getNumCaso());
                    r.createCell(1).setCellValue(
                            c.getFechaRecepcion() != null ? c.getFechaRecepcion().format(DATE_FORMATTER) : "");
                    r.createCell(2).setCellValue(c.getEstatus());
                    r.createCell(3).setCellValue(resolveLegalHierarchy(c.getComAmbLegal()));
                    r.createCell(4).setCellValue(c.getSintesis());
                }
            } else {
                sheet.createRow(rowIdx++).createCell(0).setCellValue("No registra casos como solicitante.");
            }

            // --- SECCIÓN: CASOS COMO BENEFICIARIO ---
            rowIdx++;
            Row benHeader = sheet.createRow(rowIdx++);
            benHeader.createCell(0).setCellValue("Casos como Beneficiario");
            benHeader.getCell(0).setCellStyle(headerStyle);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(rowIdx - 1, rowIdx - 1, 0, 1));

            List<CasoSummaryResponse> casosBeneficiario = casoRepository.findCasosByBeneficiarioCedula(cedula);
            if (!casosBeneficiario.isEmpty()) {
                String[] caseHeaders = { "N° Caso", "Fecha", "Estatus", "Materia", "Síntesis" };
                Row headerRow = sheet.createRow(rowIdx++);
                for (int i = 0; i < caseHeaders.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(caseHeaders[i]);
                    cell.setCellStyle(headerStyle);
                }

                for (CasoSummaryResponse c : casosBeneficiario) {
                    Row r = sheet.createRow(rowIdx++);
                    r.createCell(0).setCellValue(c.getNumCaso());
                    r.createCell(1).setCellValue(
                            c.getFechaRecepcion() != null ? c.getFechaRecepcion().format(DATE_FORMATTER) : "");
                    r.createCell(2).setCellValue(c.getEstatus());
                    r.createCell(3).setCellValue(resolveLegalHierarchy(c.getComAmbLegal()));
                    r.createCell(4).setCellValue(c.getSintesis());
                }
            } else {
                sheet.createRow(rowIdx++).createCell(0).setCellValue("No registra casos como beneficiario.");
            }

            // Adjust autosize for the first few columns
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generarHistorialCasos(String cedula, java.time.LocalDate inicio, java.time.LocalDate fin)
            throws IOException {
        List<Caso> casos = casoRepository.findCasosBySolicitanteAndFechaRange(cedula,
                inicio, fin);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Historial de Casos");

            String[] headers = { "Número de Caso", "Fecha Recepción", "Estatus", "Síntesis", "Trámite" };
            createHeaderRow(sheet, headers, workbook);

            int rowIdx = 1;
            for (Caso caso : casos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(caso.getNumCaso());
                row.createCell(1)
                        .setCellValue(caso.getFechaRecepcion() != null ? caso.getFechaRecepcion().toString() : "");
                row.createCell(2).setCellValue(caso.getEstatus());
                row.createCell(3).setCellValue(caso.getSintesis());
                row.createCell(4).setCellValue(caso.getTramite());
            }

            for (int i = 0; i < headers.length; i++)
                sheet.autoSizeColumn(i);

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generarReporteHistorialGeneral(java.time.LocalDate inicio, java.time.LocalDate fin, String username)
            throws IOException {
        List<CasoSummaryResponse> casos = casoRepository.findCasosByDatesAndUsuario(inicio, fin, username);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Historial General");

            String[] headers = { "N° Caso", "Fecha Recepción", "Estatus", "Síntesis", "Materia", "Término",
                    "Solicitante", "CI Solicitante" };
            createHeaderRow(sheet, headers, workbook);

            int rowIdx = 1;
            for (CasoSummaryResponse caso : casos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(caso.getNumCaso());
                row.createCell(1).setCellValue(
                        caso.getFechaRecepcion() != null ? caso.getFechaRecepcion().format(DATE_FORMATTER) : "");
                row.createCell(2).setCellValue(caso.getEstatus());
                row.createCell(3).setCellValue(caso.getSintesis());
                row.createCell(4).setCellValue(resolveLegalHierarchy(caso.getComAmbLegal()));
                row.createCell(5).setCellValue(caso.getTermino());
                row.createCell(6).setCellValue(caso.getNombreSolicitante());
                row.createCell(7).setCellValue(caso.getCedula());
            }

            autosizeColumns(sheet, headers.length);

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generarReportePorEstatus(String estatus) throws IOException {
        List<CasoSummaryResponse> casos = casoRepository.findAllByFilters(estatus, null, null);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Casos " + estatus);

            String[] headers = { "N° Caso", "Fecha Recepción", "Síntesis", "Estatus", "Asignado a", "Término",
                    "Solicitante" };
            createHeaderRow(sheet, headers, workbook);

            int rowIdx = 1;
            for (CasoSummaryResponse caso : casos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(caso.getNumCaso());
                row.createCell(1).setCellValue(
                        caso.getFechaRecepcion() != null ? caso.getFechaRecepcion().format(DATE_FORMATTER) : "");
                row.createCell(2).setCellValue(caso.getSintesis());
                row.createCell(3).setCellValue(caso.getEstatus());
                row.createCell(4).setCellValue(caso.getUsername());
                row.createCell(5).setCellValue(caso.getTermino());
                row.createCell(6).setCellValue(caso.getNombreSolicitante());
            }
            for (int i = 0; i < headers.length; i++)
                sheet.autoSizeColumn(i);

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generarInformeResumen(String semestre, Integer tipoCaso) throws IOException {
        List<ReporteResumenDto> resumen = casoRepository.getResumenSemestreTipo(semestre, tipoCaso);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Resumen Semestral");

            String[] headers = { "Semestre", "Tipo Caso (ID)", "Estatus", "Total Casos" };
            createHeaderRow(sheet, headers, workbook);

            int rowIdx = 1;
            long totalGeneral = 0;

            for (ReporteResumenDto item : resumen) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(semestre);
                row.createCell(1).setCellValue(tipoCaso);
                row.createCell(2).setCellValue(item.getEstatus());
                row.createCell(3).setCellValue(item.getCantidad());
                totalGeneral += item.getCantidad();
            }

            Row totalRow = sheet.createRow(rowIdx + 1);
            totalRow.createCell(2).setCellValue("TOTAL GENERAL");
            totalRow.createCell(3).setCellValue(totalGeneral);

            for (int i = 0; i < 4; i++)
                sheet.autoSizeColumn(i);

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public DashboardStatsDto getDashboardStats() {
        long totalCasos = casoRepository.countTotalCasos();
        long casosActivos = casoRepository.countByEstatus("ABIERTO"); // Or list specific active statuses
        long casosCerrados = casoRepository.countByEstatus("CERRADO");
        long totalSolicitantes = solicitanteRepository.count();

        List<clinica_juridica.backend.dto.projection.MateriaCountProjection> materiaCounts = casoRepository
                .countDistribucionMateria();
        java.util.Map<String, Long> distribucionMateria = new java.util.HashMap<>();
        for (clinica_juridica.backend.dto.projection.MateriaCountProjection p : materiaCounts) {
            distribucionMateria.put(p.getMateria(), p.getCantidad());
        }

        long totalFamilias = familiaRepository.countTotalFamilias();
        long vulnerables = familiaRepository.countFamiliasVulnerables(5000.0); // Threshold example
        double porcentajeVulnerabilidad = totalFamilias > 0 ? (double) vulnerables / totalFamilias * 100 : 0;

        return new DashboardStatsDto(totalCasos, casosActivos, casosCerrados, totalSolicitantes, distribucionMateria,
                porcentajeVulnerabilidad);
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

    private String resolveLegalHierarchy(Integer comAmbLegal) {
        if (comAmbLegal == null)
            return "N/A";

        AmbitoLegal ambito = ambitoLegalRepository.findById(comAmbLegal).orElse(null);
        if (ambito == null)
            return "ID: " + comAmbLegal;

        String hierarchy = ambito.getAmbLegal();

        if (ambito.getCodSubAmbLegal() != null) {
            SubcategoriaAmbitoLegal sub = subcategoriaAmbitoLegalRepository
                    .findById(ambito.getCodSubAmbLegal()).orElse(null);
            if (sub != null) {
                hierarchy = sub.getNombreSubcategoria() + " > " + hierarchy;
                if (sub.getCodCatAmbLegal() != null) {
                    CategoriaAmbitoLegal cat = categoriaAmbitoLegalRepository
                            .findById(sub.getCodCatAmbLegal()).orElse(null);
                    if (cat != null) {
                        hierarchy = cat.getCatAmbLegal() + " > " + hierarchy;
                        if (cat.getCodMatAmbLegal() != null) {
                            MateriaAmbitoLegal mat = materiaAmbitoLegalRepository
                                    .findById(cat.getCodMatAmbLegal()).orElse(null);
                            if (mat != null) {
                                hierarchy = mat.getMatAmbLegal() + " > " + hierarchy;
                            }
                        }
                    }
                }
            }
        }
        return hierarchy;
    }
}
