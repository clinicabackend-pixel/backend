package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("subcategoria_ambito_legal")
public class SubcategoriaAmbitoLegal {
    @Id
    private Integer codSubAmbLegal;
    private Integer codCatAmbLegal;
    private String nombreSubcategoria;

    public SubcategoriaAmbitoLegal() {
    }

    public SubcategoriaAmbitoLegal(Integer codSubAmbLegal, Integer codCatAmbLegal, String nombreSubcategoria) {
        this.codSubAmbLegal = codSubAmbLegal;
        this.codCatAmbLegal = codCatAmbLegal;
        this.nombreSubcategoria = nombreSubcategoria;
    }

    public Integer getCodSubAmbLegal() {
        return codSubAmbLegal;
    }

    public void setCodSubAmbLegal(Integer codSubAmbLegal) {
        this.codSubAmbLegal = codSubAmbLegal;
    }

    public Integer getCodCatAmbLegal() {
        return codCatAmbLegal;
    }

    public void setCodCatAmbLegal(Integer codCatAmbLegal) {
        this.codCatAmbLegal = codCatAmbLegal;
    }

    public String getNombreSubcategoria() {
        return nombreSubcategoria;
    }

    public void setNombreSubcategoria(String nombreSubcategoria) {
        this.nombreSubcategoria = nombreSubcategoria;
    }

    @Override
    public String toString() {
        return "SubcategoriaAmbitoLegal{codSubAmbLegal=" + codSubAmbLegal + ", nombreSubcategoria='"
                + nombreSubcategoria + "'}";
    }
}
