package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("materia_ambito_legal")
public class MateriaAmbitoLegal {
    @Id
    private Integer codMatAmbLegal;
    private String matAmbLegal;

    public MateriaAmbitoLegal() {
    }

    public MateriaAmbitoLegal(Integer codMatAmbLegal, String matAmbLegal) {
        this.codMatAmbLegal = codMatAmbLegal;
        this.matAmbLegal = matAmbLegal;
    }

    public Integer getCodMatAmbLegal() {
        return codMatAmbLegal;
    }

    public void setCodMatAmbLegal(Integer codMatAmbLegal) {
        this.codMatAmbLegal = codMatAmbLegal;
    }

    public String getMatAmbLegal() {
        return matAmbLegal;
    }

    public void setMatAmbLegal(String matAmbLegal) {
        this.matAmbLegal = matAmbLegal;
    }

    @Override
    public String toString() {
        return "MateriaAmbitoLegal{codMatAmbLegal=" + codMatAmbLegal + ", matAmbLegal='" + matAmbLegal + "'}";
    }
}
