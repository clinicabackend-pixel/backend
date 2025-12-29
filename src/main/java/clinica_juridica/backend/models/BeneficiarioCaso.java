package clinica_juridica.backend.models;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("beneficiarios_casos")
public class BeneficiarioCaso {

    @Column("cedula")
    private String cedula;

    @Column("num_caso")
    private String numCaso;

    @Column("tipo_beneficiario")
    private String tipoBeneficiario;

    @Column("parentesco")
    private String parentesco;

    public BeneficiarioCaso() {
    }

    public BeneficiarioCaso(String cedula, String numCaso, String tipoBeneficiario, String parentesco) {
        this.cedula = cedula;
        this.numCaso = numCaso;
        this.tipoBeneficiario = tipoBeneficiario;
        this.parentesco = parentesco;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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
