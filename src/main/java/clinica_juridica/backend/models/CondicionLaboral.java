package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("condicion_laboral")
public class CondicionLaboral {
    @Id
    private Integer idCondicion;
    private String condicion;
    private String estatus;

    public CondicionLaboral() {
    }

    public CondicionLaboral(Integer idCondicion, String condicion, String estatus) {
        this.idCondicion = idCondicion;
        this.condicion = condicion;
        this.estatus = estatus;
    }

    public Integer getIdCondicion() {
        return idCondicion;
    }

    public void setIdCondicion(Integer idCondicion) {
        this.idCondicion = idCondicion;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "CondicionLaboral{idCondicion=" + idCondicion + ", condicion='" + condicion + "'}";
    }
}
