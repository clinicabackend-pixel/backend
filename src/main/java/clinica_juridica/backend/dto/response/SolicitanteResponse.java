package clinica_juridica.backend.dto.response;

import java.time.LocalDate;

public record SolicitanteResponse(
                String idSolicitante,
                String nombre,
                String sexo,
                LocalDate fechaNacimiento,
                String estadoCivil,
                Integer idParroquia,
                Integer idNivelEducativo,
                String idVivienda,
                String idTrabajo) {
}
