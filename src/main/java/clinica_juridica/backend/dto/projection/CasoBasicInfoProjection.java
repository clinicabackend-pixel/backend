package clinica_juridica.backend.dto.projection;

import java.time.LocalDate;

public record CasoBasicInfoProjection(
        String numCaso,
        LocalDate fechaRecepcion,
        String estatus,
        String sintesis,
        Integer cantBeneficiarios,
        String idSolicitante,
        String nombreSolicitante,
        String materia,
        String nombreCentro) {
}
