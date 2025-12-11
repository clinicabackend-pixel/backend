package clinica_juridica.backend.dto.response;

import java.time.LocalDate;

public record AccionResponse(
        Integer idAccion,
        LocalDate fecha,
        String descripcion,
        String tipoAccion) {
}
