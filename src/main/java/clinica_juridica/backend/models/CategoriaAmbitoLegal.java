package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("categoria_ambito_legal")
public class CategoriaAmbitoLegal {
    @Id
    private Integer codCatAmbLegal;
    private Integer codMatAmbLegal;
    private String catAmbLegal;

    public CategoriaAmbitoLegal() {
    }

    public CategoriaAmbitoLegal(Integer codCatAmbLegal, Integer codMatAmbLegal, String catAmbLegal) {
        this.codCatAmbLegal = codCatAmbLegal;
        this.codMatAmbLegal = codMatAmbLegal;
        this.catAmbLegal = catAmbLegal;
    }

    public Integer getCodCatAmbLegal() {
        return codCatAmbLegal;
    }

    public void setCodCatAmbLegal(Integer codCatAmbLegal) {
        this.codCatAmbLegal = codCatAmbLegal;
    }

    public Integer getCodMatAmbLegal() {
        return codMatAmbLegal;
    }

    public void setCodMatAmbLegal(Integer codMatAmbLegal) {
        this.codMatAmbLegal = codMatAmbLegal;
    }

    public String getCatAmbLegal() {
        return catAmbLegal;
    }

    public void setCatAmbLegal(String catAmbLegal) {
        this.catAmbLegal = catAmbLegal;
    }

    @Override
    public String toString() {
        return "CategoriaAmbitoLegal{codCatAmbLegal=" + codCatAmbLegal + ", catAmbLegal='" + catAmbLegal + "'}";
    }
}
