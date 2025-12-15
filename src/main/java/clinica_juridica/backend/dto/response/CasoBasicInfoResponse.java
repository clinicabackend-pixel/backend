package clinica_juridica.backend.dto.response;

import java.time.LocalDate;

public record CasoBasicInfoResponse(
        String numCaso,
        LocalDate fechaRecepcion,
        String estatus,
        String sintesis,
        Integer cantBeneficiarios,
        String idSolicitante,
        String nombreSolicitante,
        String materia) {
}
