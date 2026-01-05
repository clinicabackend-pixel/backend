package clinica_juridica.backend.dto.request;

public class BeneficiarioUpdateRequest {
    private String tipoBeneficiario;
    private String parentesco;

    public BeneficiarioUpdateRequest() {
    }

    public BeneficiarioUpdateRequest(String tipoBeneficiario, String parentesco) {
        this.tipoBeneficiario = tipoBeneficiario;
        this.parentesco = parentesco;
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
