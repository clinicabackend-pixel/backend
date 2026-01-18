package clinica_juridica.backend.dto.response;

public record ViviendaDto(
                String cedula,
                String tipoVivienda,
                Integer cantHabitaciones,
                Integer cantBanos,
                String materialPiso,
                String materialParedes,
                String materialTecho,
                String servicioAgua,
                String eliminacionExcretas,
                String aseoUrbano) {
}
