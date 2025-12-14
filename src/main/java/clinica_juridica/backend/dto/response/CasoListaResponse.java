package clinica_juridica.backend.dto.response;

public record CasoListaResponse(
        String numCaso,
        String materia,
        String cedula,
        String nombre
    ) {}