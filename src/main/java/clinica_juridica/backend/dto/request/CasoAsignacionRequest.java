package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de asignación de estudiante a caso")
public class CasoAsignacionRequest {

    @Schema(description = "Username del estudiante", example = "estudiante1")
    private String username;

    @Schema(description = "Término académico", example = "2024-1")
    private String termino;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }
}
