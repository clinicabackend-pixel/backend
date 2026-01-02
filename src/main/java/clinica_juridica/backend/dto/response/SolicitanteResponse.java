package clinica_juridica.backend.dto.response;

import java.time.LocalDate;

public record SolicitanteResponse(
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
        String estadoResidencia,
        String municipioResidencia,
        String parroquiaResidencia,
        String tipoVivienda) {
}
