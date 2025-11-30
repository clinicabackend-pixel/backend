package clinica_juridica.backend.domain.models;

import java.time.LocalDate;

public class CambioEstatus {
    private String idUsuario;
    private String numCaso;
    private LocalDate fechaCambio;
    private String statusAnt;

    public CambioEstatus() {
    }

    public CambioEstatus(String idUsuario, String numCaso, LocalDate fechaCambio, String statusAnt) {
        this.idUsuario = idUsuario;
        this.numCaso = numCaso;
        this.fechaCambio = fechaCambio;
        this.statusAnt = statusAnt;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public LocalDate getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDate fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getStatusAnt() {
        return statusAnt;
    }

    public void setStatusAnt(String statusAnt) {
        this.statusAnt = statusAnt;
    }
}

