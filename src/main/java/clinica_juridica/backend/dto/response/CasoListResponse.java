package clinica_juridica.backend.dto.response;

import java.time.LocalDate;

public record CasoListResponse(
        String numCaso,
        String materia,
        String cedula,
        String nombre,
        LocalDate fecha,
        String estatus) {
}