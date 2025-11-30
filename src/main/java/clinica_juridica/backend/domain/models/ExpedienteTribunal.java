package clinica_juridica.backend.domain.models;

import java.time.LocalDate;

public class ExpedienteTribunal {
    private String numExpediente;
    private LocalDate fechaCreacion;
    private Integer idTribunal;
    private String idCaso;

    public ExpedienteTribunal() {
    }

    public ExpedienteTribunal(String numExpediente, LocalDate fechaCreacion, 
                              Integer idTribunal, String idCaso) {
        this.numExpediente = numExpediente;
        this.fechaCreacion = fechaCreacion;
        this.idTribunal = idTribunal;
        this.idCaso = idCaso;
    }

    public String getNumExpediente() {
        return numExpediente;
    }

    public void setNumExpediente(String numExpediente) {
        this.numExpediente = numExpediente;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdTribunal() {
        return idTribunal;
    }

    public void setIdTribunal(Integer idTribunal) {
        this.idTribunal = idTribunal;
    }

    public String getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(String idCaso) {
        this.idCaso = idCaso;
    }
}
