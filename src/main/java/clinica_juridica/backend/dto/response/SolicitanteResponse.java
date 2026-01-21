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
                String condicionLaboral,
                String condicionActividad,
                String telfCasa,
                String telfCelular,
                String email,
                Integer idParroquia,
                Integer idMunicipio,
                Integer idEstado,
                String nivelEducativo,
                String apellido,
                String nombreParroquia,
                String nombreMunicipio,
                String nombreEstado) {
}
