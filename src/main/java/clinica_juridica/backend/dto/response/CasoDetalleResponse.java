package clinica_juridica.backend.dto.response;

import java.time.LocalDate;
import java.util.List;

public record CasoDetalleResponse(
                String numCaso,
                LocalDate fechaRecepcion,
                String estatus,
                String sintesis,
                Integer cantBeneficiarios,
                String idSolicitante,
                String nombreSolicitante,
                String materia,
                String nombreCentro,
                ExpedienteResponse expediente,
                List<BeneficiarioResponse> beneficiarios,
                List<EstudianteAsignadoResponse> estudiantes,
                List<CitaResponse> citas,
                List<AccionResponse> acciones) {
}
