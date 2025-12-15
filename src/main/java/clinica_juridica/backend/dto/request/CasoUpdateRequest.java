package clinica_juridica.backend.dto.request;

import java.time.LocalDate;

public record CasoUpdateRequest(
        LocalDate fechaInicio,
        String estado,
        String descripcion,
        String idSolicitante,
        Integer idAmbitoLegal) {
}
