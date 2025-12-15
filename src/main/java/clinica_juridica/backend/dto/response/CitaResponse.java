package clinica_juridica.backend.dto.response;

import java.time.LocalDateTime;

public record CitaResponse(
        LocalDateTime fecha,
        String estado,
        String orientacion,
        String tramite,
        String nombreAtendio) {
}
