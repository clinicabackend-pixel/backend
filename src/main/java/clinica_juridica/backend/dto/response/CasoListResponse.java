package clinica_juridica.backend.dto.response;

public record CasoListResponse(
        String numCaso,
        String materia,
        String cedula,
        String nombre
    ) {}