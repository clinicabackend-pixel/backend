package clinica_juridica.backend.dto.response;

import java.time.LocalDate;

public record ExpedienteResponse(
        String numExpediente,
        LocalDate fechaCreacion,
        String nombreTribunal) {
}
