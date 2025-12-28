package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class CasoEstatusUpdateRequest {

    @Schema(description = "Nuevo estatus para el caso", example = "ACTIVO", requiredMode = Schema.RequiredMode.REQUIRED)
    private String estatus;

    public CasoEstatusUpdateRequest() {
    }

    public CasoEstatusUpdateRequest(String estatus) {
        this.estatus = estatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
