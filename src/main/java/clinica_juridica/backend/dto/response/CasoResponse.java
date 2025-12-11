package clinica_juridica.backend.dto.response;

import java.time.LocalDate;

public record CasoResponse(
        String numCaso,
        LocalDate fechaInicio,
        String estado,
        String descripcion,
        String idSolicitante,
        Integer idAmbitoLegal) {
}
