package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tipos_categorias_viviendas")
public class TipoCategoriaVivienda {
    @Id
    private Integer idTipoCat;
    private String tipoCategoria;

    public TipoCategoriaVivienda() {
    }

    public TipoCategoriaVivienda(Integer idTipoCat, String tipoCategoria) {
        this.idTipoCat = idTipoCat;
        this.tipoCategoria = tipoCategoria;
    }

    public Integer getIdTipoCat() {
        return idTipoCat;
    }

    public void setIdTipoCat(Integer idTipoCat) {
        this.idTipoCat = idTipoCat;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    @Override
    public String toString() {
        return "TipoCategoriaVivienda{idTipoCat=" + idTipoCat + ", tipoCategoria='" + tipoCategoria + "'}";
    }
}
