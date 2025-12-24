package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("estado_civil")
public class EstadoCivil {
    @Id
    private Integer idEstadoCivil;
    private String descripcion;
    private String estatus;

    public EstadoCivil() {
    }

    public EstadoCivil(Integer idEstadoCivil, String descripcion, String estatus) {
        this.idEstadoCivil = idEstadoCivil;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }

    public Integer getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(Integer idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
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
        return "EstadoCivil{idEstadoCivil=" + idEstadoCivil + ", descripcion='" + descripcion + "'}";
    }
}
