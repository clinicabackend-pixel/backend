package clinica_juridica.backend.domain.entities;

import java.time.LocalDate;

public class CitaAtendida {
    private String numCaso;
    private LocalDate fecha;
    private String idUsuario;

    public CitaAtendida() {
    }

    public CitaAtendida(String numCaso, LocalDate fecha, String idUsuario) {
        this.numCaso = numCaso;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}

