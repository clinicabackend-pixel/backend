package clinica_juridica.backend.domain.entities;

import java.time.LocalDate;

public class Cita {
    private LocalDate fecha;
    private String numCaso;
    private String estado;
    private String orientacion;

    public Cita() {
    }

    public Cita(LocalDate fecha, String numCaso, String estado, String orientacion) {
        this.fecha = fecha;
        this.numCaso = numCaso;
        this.estado = estado;
        this.orientacion = orientacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
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

