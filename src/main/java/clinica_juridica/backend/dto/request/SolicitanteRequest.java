package clinica_juridica.backend.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de creación de un nuevo solicitante")
public record SolicitanteRequest(
                @Schema(description = "Cédula de identidad", example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED) String cedula,

                @Schema(description = "Nombre completo", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED) String nombre,

                @Schema(description = "Sexo del solicitante", example = "Masculino") String sexo,

                @Schema(description = "ID del estado civil", example = "1") Integer idEstadoCivil,

                @Schema(description = "Fecha de nacimiento", example = "1990-01-01") LocalDate fechaNacimiento,

                @Schema(description = "Indica si vive en concubinato", example = "false") Boolean concubinato,

                @Schema(description = "Nacionalidad", example = "Venezolano") String nacionalidad,

                @Schema(description = "Indica si trabaja actualmente", example = "true") Boolean trabaja,

                @Schema(description = "ID de la condición laboral", example = "1") Integer idCondicion,

                @Schema(description = "ID de la condición de actividad", example = "1") Integer idCondicionActividad,

                @Schema(description = "Teléfono de habitación", example = "02121234567") String telfCasa,

                @Schema(description = "Teléfono celular", example = "04141234567") String telfCelular,

                @Schema(description = "Correo electrónico", example = "juan.perez@email.com") String email,

                @Schema(description = "ID de la parroquia de residencia", example = "5") Integer idParroquia) {
}
