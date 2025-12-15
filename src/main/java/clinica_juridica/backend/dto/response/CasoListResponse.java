package clinica_juridica.backend.dto.response;

import java.time.LocalDate;
import java.util.List;

public record CasoListResponse(
        String numCaso,
        String materia,
        String cedula,
        String nombre,
        LocalDate fecha,
        String estatus,
        List<EstudianteResumidoResponse> estudiantes) {
}