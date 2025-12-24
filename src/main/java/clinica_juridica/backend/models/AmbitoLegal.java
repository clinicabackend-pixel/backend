package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("ambito_legal")
public class AmbitoLegal {
    @Id
    private Integer codAmbLegal;
    private Integer codSubAmbLegal;
    private String ambLegal;

    public AmbitoLegal() {
    }

    public AmbitoLegal(Integer codAmbLegal, Integer codSubAmbLegal, String ambLegal) {
        this.codAmbLegal = codAmbLegal;
        this.codSubAmbLegal = codSubAmbLegal;
        this.ambLegal = ambLegal;
    }

    public Integer getCodAmbLegal() {
        return codAmbLegal;
    }

    public void setCodAmbLegal(Integer codAmbLegal) {
        this.codAmbLegal = codAmbLegal;
    }

    public Integer getCodSubAmbLegal() {
        return codSubAmbLegal;
    }

    public void setCodSubAmbLegal(Integer codSubAmbLegal) {
        this.codSubAmbLegal = codSubAmbLegal;
    }

    public String getAmbLegal() {
        return ambLegal;
    }

    public void setAmbLegal(String ambLegal) {
        this.ambLegal = ambLegal;
    }

    @Override
    public String toString() {
        return "AmbitoLegal{codAmbLegal=" + codAmbLegal + ", ambLegal='" + ambLegal + "'}";
    }
}
