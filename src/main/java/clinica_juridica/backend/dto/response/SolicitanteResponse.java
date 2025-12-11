package clinica_juridica.backend.dto.response;

import java.time.LocalDate;

public record SolicitanteResponse(
        String idSolicitante,
        String nombre,
        String sexo,
        LocalDate fechaNacimiento,
        String estadoCivil,
        Integer numHijos,
        String direccion,
        Integer idParroquia,
        Integer idNivelEducativo,
        String idVivienda,
        String idTrabajo,
        Integer ingresoFamiliar) {
}
