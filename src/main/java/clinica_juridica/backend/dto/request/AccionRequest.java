package clinica_juridica.backend.dto.request;

import java.time.LocalDate;

public record AccionRequest(
        Integer idAccion,
        LocalDate fecha,
        String descripcion,
        String tipoAccion) {
}
