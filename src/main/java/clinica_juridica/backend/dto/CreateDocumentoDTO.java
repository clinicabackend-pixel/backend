package clinica_juridica.backend.dto;

import java.time.LocalDate;

public class CreateDocumentoDTO {
    private LocalDate fechaRegistro;
    private Integer folioIni;
    private Integer folioFin;
    private String titulo;
    private String observacion;
    private String username;

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getFolioIni() {
        return folioIni;
    }

    public void setFolioIni(Integer folioIni) {
        this.folioIni = folioIni;
    }

    public Integer getFolioFin() {
        return folioFin;
    }

    public void setFolioFin(Integer folioFin) {
        this.folioFin = folioFin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
}
