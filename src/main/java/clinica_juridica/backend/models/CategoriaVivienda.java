package clinica_juridica.backend.models;

import org.springframework.data.relational.core.mapping.Table;

@Table("categorias_de_vivienda")
public class CategoriaVivienda {
    // Composite key handling typically requires more complex setup in Spring Data
    // JDBC/JPA
    // but for raw JDBC POJO, we just map fields.
    private Integer idCatVivienda;
    private Integer idTipoCat;
    private String descripcion;
    private String estatus;

    public CategoriaVivienda() {
    }

    public CategoriaVivienda(Integer idCatVivienda, Integer idTipoCat, String descripcion, String estatus) {
        this.idCatVivienda = idCatVivienda;
        this.idTipoCat = idTipoCat;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }

    public Integer getIdCatVivienda() {
        return idCatVivienda;
    }

    public void setIdCatVivienda(Integer idCatVivienda) {
        this.idCatVivienda = idCatVivienda;
    }

    public Integer getIdTipoCat() {
        return idTipoCat;
    }

    public void setIdTipoCat(Integer idTipoCat) {
        this.idTipoCat = idTipoCat;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "CategoriaVivienda{idCatVivienda=" + idCatVivienda + ", idTipoCat=" + idTipoCat + ", descripcion='"
                + descripcion + "'}";
    }
}
