package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("encuentros")
public class Encuentro {
    @Id
    private Integer idEncuentros;
    private String numCaso;
    private LocalDate fechaAtencion;
    private LocalDate fechaProxima;
    private String orientacion;
    private String observacion;
    private String username;

    public Encuentro() {
    }

    public Encuentro(Integer idEncuentros, String numCaso, LocalDate fechaAtencion, LocalDate fechaProxima,
            String orientacion, String observacion, String username) {
        this.idEncuentros = idEncuentros;
        this.numCaso = numCaso;
        this.fechaAtencion = fechaAtencion;
        this.fechaProxima = fechaProxima;
        this.orientacion = orientacion;
        this.observacion = observacion;
        this.username = username;
    }

    public Integer getIdEncuentros() {
        return idEncuentros;
    }

    public void setIdEncuentros(Integer idEncuentros) {
        this.idEncuentros = idEncuentros;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public LocalDate getFechaProxima() {
        return fechaProxima;
    }

    public void setFechaProxima(LocalDate fechaProxima) {
        this.fechaProxima = fechaProxima;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Encuentro{idEncuentros=" + idEncuentros + ", numCaso='" + numCaso + "'}";
    }
}
