package clinica_juridica.backend.dto.request;

import java.time.LocalDate;
import java.util.List;

public class EncuentroCreateRequest {
    private LocalDate fechaAtencion;
    private LocalDate fechaProxima;
    private String orientacion;
    private String observacion;
    private String username; // The one who registers
    private List<String> atendidos; // List of usernames who attended

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

    public List<String> getAtendidos() {
        return atendidos;
    }

    public void setAtendidos(List<String> atendidos) {
        this.atendidos = atendidos;
    }
}
