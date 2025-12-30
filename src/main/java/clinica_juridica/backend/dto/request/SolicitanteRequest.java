package clinica_juridica.backend.dto.request;

import java.time.LocalDate;

public record SolicitanteRequest(
        String cedula,
        String nombre,
        String sexo,
        String estadoCivil,
        LocalDate fechaNacimiento,
        Boolean concubinato,
        String nacionalidad,
        Boolean trabaja,
        String condicionTrabajo,
        String telfCasa,
        String telfCelular,
        String email,
        String parroquiaResidencia) {
}
