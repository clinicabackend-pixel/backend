package clinica_juridica.backend.dto.response;

public record EstudianteAsignadoResponse(
        String idEstudiante,
        String nombre,
        Integer semestre,
        Integer materia,
        Integer seccion) {
}
