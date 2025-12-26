package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de asignación de supervisor (profesor) a caso")
public class CasoSupervisionRequest {

    @Schema(description = "Número de caso", example = "C-2024-001")
    private String numCaso;

    @Schema(description = "Username del profesor", example = "profesor1")
    private String username;

    @Schema(description = "Término académico", example = "2024-1")
    private String termino;

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

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
