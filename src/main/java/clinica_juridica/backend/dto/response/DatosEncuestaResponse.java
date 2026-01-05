package clinica_juridica.backend.dto.response;

import clinica_juridica.backend.dto.request.DatosEncuestaRequest;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con los datos de encuesta cargados")
public class DatosEncuestaResponse extends DatosEncuestaRequest {
    // Inherits everything from Request, easier for now since they mirror each
    // other.
    // Can extend if needed.
}
