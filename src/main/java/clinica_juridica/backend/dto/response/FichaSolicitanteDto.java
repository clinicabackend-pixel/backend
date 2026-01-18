package clinica_juridica.backend.dto.response;

import java.util.List;

public record FichaSolicitanteDto(
        SolicitanteResponse datosPersonales,
        EncuestaResponse datosSocioeconomicos,
        List<CasoSummaryResponse> historialCasos) {
}
