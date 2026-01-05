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
        Integer idEstadoCivil,
        Integer idParroquia,
        Integer idMunicipio,
        Integer idEstado,
        Integer idCondicion,
        Integer idCondicionActividad,
        Integer idNivel) {
}
