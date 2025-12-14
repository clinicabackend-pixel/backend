package clinica_juridica.backend.dto.request;

import java.time.LocalDate;

public record CasoCreateRequest(
        LocalDate fechaInicio,
        String estado,
        String descripcion,
        String idSolicitante,
        Integer idAmbitoLegal) {
}
