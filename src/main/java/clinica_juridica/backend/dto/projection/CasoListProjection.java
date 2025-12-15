package clinica_juridica.backend.dto.projection;

import java.time.LocalDate;

public record CasoListProjection(
        String numCaso,
        String materia,
        String cedula,
        String nombre,
        LocalDate fecha,
        String estatus) {
}
