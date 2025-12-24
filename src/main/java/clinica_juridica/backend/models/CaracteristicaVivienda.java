package clinica_juridica.backend.models;

import org.springframework.data.relational.core.mapping.Table;

@Table("caracteristicas_viviendas")
public class CaracteristicaVivienda {
    // Composite PK: cedula, id_tipo_cat, id_cat_vivienda
    private String cedula;
    private Integer idTipoCat;
    private Integer idCatVivienda;

    public CaracteristicaVivienda() {
    }

    public CaracteristicaVivienda(String cedula, Integer idTipoCat, Integer idCatVivienda) {
        this.cedula = cedula;
        this.idTipoCat = idTipoCat;
        this.idCatVivienda = idCatVivienda;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Integer getIdTipoCat() {
        return idTipoCat;
    }

    public void setIdTipoCat(Integer idTipoCat) {
        this.idTipoCat = idTipoCat;
    }

    public Integer getIdCatVivienda() {
        return idCatVivienda;
    }

    public void setIdCatVivienda(Integer idCatVivienda) {
        this.idCatVivienda = idCatVivienda;
    }

    @Override
    public String toString() {
        return "CaracteristicaVivienda{cedula='" + cedula + "', idTipoCat=" + idTipoCat + ", idCatVivienda="
                + idCatVivienda + "}";
    }
}
