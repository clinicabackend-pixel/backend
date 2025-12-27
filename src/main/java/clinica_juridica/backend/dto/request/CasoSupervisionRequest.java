package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de asignación de supervisor (profesor) a caso")
public class CasoSupervisionRequest {

    // numCaso removed as it is passed in the URL

    @Schema(description = "Username del profesor", example = "profesor1")
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
