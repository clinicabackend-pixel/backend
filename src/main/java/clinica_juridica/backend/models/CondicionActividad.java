package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("condicion_actividad")
public class CondicionActividad {
    @Id
    @Column("id_condicion_actividad")
    private Integer idCondicionActividad;
    @Column("nombre_actividad")
    private String nombreActividad;
    @Column("estatus")
    private String estatus;

    public CondicionActividad() {
    }

    public CondicionActividad(Integer idCondicionActividad, String nombreActividad, String estatus) {
        this.idCondicionActividad = idCondicionActividad;
        this.nombreActividad = nombreActividad;
        this.estatus = estatus;
    }

    public Integer getIdCondicionActividad() {
        return idCondicionActividad;
    }

    public void setIdCondicionActividad(Integer idCondicionActividad) {
        this.idCondicionActividad = idCondicionActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "CondicionActividad{idCondicionActividad=" + idCondicionActividad + ", nombreActividad='"
                + nombreActividad + "'}";
    }
}
