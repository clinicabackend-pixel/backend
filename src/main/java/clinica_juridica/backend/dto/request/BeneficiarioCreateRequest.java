package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos del beneficiario del caso para creación")
public class BeneficiarioCreateRequest {

    @Schema(description = "Cédula del beneficiario", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cedula;

    @Schema(description = "Tipo de beneficiario (ej. HIJO, CONYUGE)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tipoBeneficiario;

    @Schema(description = "Parentesco con el solicitante", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentesco;

    public BeneficiarioCreateRequest() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
