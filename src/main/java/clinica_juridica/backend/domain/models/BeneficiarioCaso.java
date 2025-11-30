package clinica_juridica.backend.domain.models;

public class BeneficiarioCaso {
    private String idBeneficiario;
    private String numCaso;
    private String tipoBeneficiario;
    private String parentesco;

    public BeneficiarioCaso() {
    }

    public BeneficiarioCaso(String idBeneficiario, String numCaso, 
                            String tipoBeneficiario, String parentesco) {
        this.idBeneficiario = idBeneficiario;
        this.numCaso = numCaso;
        this.tipoBeneficiario = tipoBeneficiario;
        this.parentesco = parentesco;
    }

    public String getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(String idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
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

