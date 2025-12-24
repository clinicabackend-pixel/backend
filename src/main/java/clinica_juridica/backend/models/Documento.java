package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("documentos")
public class Documento {
    @Id
    private Integer idDocumento;
    private String numCaso;
    private LocalDate fechaRegistro;
    private Integer folioIni;
    private Integer folioFin;
    private String titulo;
    private String observacion;
    private String username;

    public Documento() {
    }

    public Documento(Integer idDocumento, String numCaso, LocalDate fechaRegistro, Integer folioIni, Integer folioFin,
            String titulo, String observacion, String username) {
        this.idDocumento = idDocumento;
        this.numCaso = numCaso;
        this.fechaRegistro = fechaRegistro;
        this.folioIni = folioIni;
        this.folioFin = folioFin;
        this.titulo = titulo;
        this.observacion = observacion;
        this.username = username;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

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

    @Override
    public String toString() {
        return "Documento{idDocumento=" + idDocumento + ", titulo='" + titulo + "'}";
    }
}
