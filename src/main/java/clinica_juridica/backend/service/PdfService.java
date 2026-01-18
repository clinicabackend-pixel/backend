package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.response.CasoDetalleResponse;
import clinica_juridica.backend.dto.response.CasoSummaryResponse;
import clinica_juridica.backend.dto.response.EncuestaResponse;
import clinica_juridica.backend.dto.response.FichaSolicitanteDto;
import clinica_juridica.backend.dto.response.SolicitanteResponse;
import clinica_juridica.backend.exception.ResourceNotFoundException;
import clinica_juridica.backend.models.AmbitoLegal;
import clinica_juridica.backend.models.CategoriaAmbitoLegal;
import clinica_juridica.backend.models.MateriaAmbitoLegal;
import clinica_juridica.backend.models.SubcategoriaAmbitoLegal;
import clinica_juridica.backend.repository.AmbitoLegalRepository;
import clinica_juridica.backend.repository.CasoRepository;
import clinica_juridica.backend.repository.CategoriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.MateriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.SubcategoriaAmbitoLegalRepository;
import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PdfService {

    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);

    private final SolicitanteService solicitanteService;
    private final CasoRepository casoRepository;
    private final CasoService casoService;
    private final SpringTemplateEngine templateEngine;

    // Repositories for resolving legal hierarchy
    private final AmbitoLegalRepository ambitoLegalRepository;
    private final SubcategoriaAmbitoLegalRepository subcategoriaAmbitoLegalRepository;
    private final CategoriaAmbitoLegalRepository categoriaAmbitoLegalRepository;
    private final MateriaAmbitoLegalRepository materiaAmbitoLegalRepository;

    public PdfService(SolicitanteService solicitanteService,
            CasoRepository casoRepository,
            CasoService casoService,
            SpringTemplateEngine templateEngine,
            AmbitoLegalRepository ambitoLegalRepository,
            SubcategoriaAmbitoLegalRepository subcategoriaAmbitoLegalRepository,
            CategoriaAmbitoLegalRepository categoriaAmbitoLegalRepository,
            MateriaAmbitoLegalRepository materiaAmbitoLegalRepository) {
        this.solicitanteService = solicitanteService;
        this.casoRepository = casoRepository;
        this.casoService = casoService;
        this.templateEngine = templateEngine;
        this.ambitoLegalRepository = ambitoLegalRepository;
        this.subcategoriaAmbitoLegalRepository = subcategoriaAmbitoLegalRepository;
        this.categoriaAmbitoLegalRepository = categoriaAmbitoLegalRepository;
        this.materiaAmbitoLegalRepository = materiaAmbitoLegalRepository;
    }

    public byte[] generarFichaPdf(@NonNull String cedula) {
        // 1. Fetch Data
        SolicitanteResponse datosPersonales = solicitanteService.getById(cedula)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitante no encontrado: " + cedula));

        EncuestaResponse datosSocioeconomicos = solicitanteService.getEncuesta(cedula)
                .orElse(null);

        List<CasoSummaryResponse> historialCasos = casoRepository.findCasosBySolicitanteCedula(cedula);

        FichaSolicitanteDto data = new FichaSolicitanteDto(datosPersonales, datosSocioeconomicos, historialCasos);

        // 2. Prepare Context
        Context context = new Context();
        context.setVariable("datos", data);
        context.setVariable("fechaEmision", LocalDate.now());

        return generatePdfFromTemplate("ficha_solicitante", context);
    }

    public byte[] generarReporteCasoPdf(String id) {
        CasoDetalleResponse datos = casoService.getCasoDetalle(id);

        // Fetch extended Solicitante info
        String cedula = datos.getCaso().getCedula();
        SolicitanteResponse solicitante = null;
        if (cedula != null) {
            solicitante = solicitanteService.getById(cedula).orElse(null);
        }

        // Resolve Materia string
        String materiaCompleta = resolveLegalHierarchy(datos.getCaso().getComAmbLegal());

        Context context = new Context();
        context.setVariable("datos", datos);
        context.setVariable("solicitante", solicitante);
        context.setVariable("materiaCompleta", materiaCompleta);
        context.setVariable("fechaEmision", LocalDate.now());

        return generatePdfFromTemplate("reporte_caso", context);
    }

    // ... (generatePdfFromTemplate remains same)

    private byte[] generatePdfFromTemplate(String templateName, Context context) {
        // 3. Process HTML
        String htmlContent = templateEngine.process(templateName, context);

        // 4. Generate PDF
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);
            return os.toByteArray();
        } catch (DocumentException | IOException e) {
            logger.error("Error generando PDF: " + templateName, e);
            throw new RuntimeException("Error generando el PDF", e);
        }
    }

    private String resolveLegalHierarchy(Integer comAmbLegal) {
        if (comAmbLegal == null)
            return "N/A";

        AmbitoLegal ambito = ambitoLegalRepository.findById(comAmbLegal).orElse(null);
        if (ambito == null)
            return "ID: " + comAmbLegal;

        String hierarchy = ambito.getAmbLegal();

        Integer codSub = ambito.getCodSubAmbLegal();
        if (codSub != null) {
            SubcategoriaAmbitoLegal sub = subcategoriaAmbitoLegalRepository
                    .findById(codSub).orElse(null);
            if (sub != null) {
                hierarchy = sub.getNombreSubcategoria() + " > " + hierarchy;

                Integer codCat = sub.getCodCatAmbLegal();
                if (codCat != null) {
                    CategoriaAmbitoLegal cat = categoriaAmbitoLegalRepository
                            .findById(codCat).orElse(null);
                    if (cat != null) {
                        hierarchy = cat.getCatAmbLegal() + " > " + hierarchy;

                        Integer codMat = cat.getCodMatAmbLegal();
                        if (codMat != null) {
                            MateriaAmbitoLegal mat = materiaAmbitoLegalRepository
                                    .findById(codMat).orElse(null);
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
