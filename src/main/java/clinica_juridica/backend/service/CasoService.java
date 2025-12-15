package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.response.CasoListResponse;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;

import java.util.List;

import org.springframework.stereotype.Service;
import clinica_juridica.backend.dto.request.BeneficiarioRequest;

@Service
public class CasoService {

    private final CasoRepository casoRepository;
    private final BeneficiarioCasoRepository beneficiarioCasoRepository;
    private final AsignacionRepository asignacionRepository;
    private final CitaRepository citaRepository;
    private final AccionRepository accionRepository;
    private final ExpedienteTribunalRepository expedienteTribunalRepository;

    public CasoService(CasoRepository casoRepository,
            BeneficiarioCasoRepository beneficiarioCasoRepository,
            AsignacionRepository asignacionRepository,
            CitaRepository citaRepository,
            AccionRepository accionRepository,
            ExpedienteTribunalRepository expedienteTribunalRepository) {
        this.casoRepository = casoRepository;
        this.beneficiarioCasoRepository = beneficiarioCasoRepository;
        this.asignacionRepository = asignacionRepository;
        this.citaRepository = citaRepository;
        this.accionRepository = accionRepository;
        this.expedienteTribunalRepository = expedienteTribunalRepository;
    }

    public List<CasoListResponse> findAllWithSolicitanteInfo() {
        return casoRepository.findAllWithSolicitanteInfo();
    }

    public String createCaso(Caso caso) {
        return casoRepository.createCaso(caso);
    }

    public String updateCaso(Caso caso) {
        return casoRepository.updateCaso(caso);
    }

    public String addBeneficiario(String numCaso, BeneficiarioRequest request) {
        return beneficiarioCasoRepository.addBeneficiario(request.idBeneficiario(), numCaso, request.tipo(),
                request.parentesco());
    }

    public clinica_juridica.backend.dto.response.CasoDetalleResponse getCasoDetalle(String numCaso) {
        var basicInfo = casoRepository.findCasoDetalleBasic(numCaso)
                .orElseThrow(() -> new RuntimeException("Caso no encontrado: " + numCaso));

        var asignaciones = asignacionRepository.findByNumCaso(numCaso);
        var beneficiarios = beneficiarioCasoRepository.findByNumCaso(numCaso);
        var citas = citaRepository.findByNumCaso(numCaso);
        var acciones = accionRepository.findByNumCaso(numCaso);
        var expediente = expedienteTribunalRepository.findByNumCaso(numCaso).orElse(null);

        return new clinica_juridica.backend.dto.response.CasoDetalleResponse(
                basicInfo.numCaso(),
                basicInfo.fechaRecepcion(),
                basicInfo.estatus(),
                basicInfo.sintesis(),
                basicInfo.cantBeneficiarios(),
                basicInfo.idSolicitante(),
                basicInfo.nombreSolicitante(),
                basicInfo.materia(),
                expediente,
                beneficiarios,
                asignaciones,
                citas,
                acciones);
    }
}
