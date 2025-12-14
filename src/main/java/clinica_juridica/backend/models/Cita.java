package clinica_juridica.backend.models;

import java.time.LocalDateTime;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("citas")
public class Cita {
    @Column("fecha")
    private LocalDateTime fecha;

    @Column("num_caso")
    private String numCaso;

    @Column("tramite")
    private String tramite;

    @Column("estado")
    private String estado;

    @Column("orientacion")
    private String orientacion;

    public Cita() {
    }

    public Cita(LocalDateTime fecha, String numCaso, String tramite, String estado, String orientacion) {
        this.fecha = fecha;
        this.numCaso = numCaso;
        this.tramite = tramite;
        this.estado = estado;
        this.orientacion = orientacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }
}
