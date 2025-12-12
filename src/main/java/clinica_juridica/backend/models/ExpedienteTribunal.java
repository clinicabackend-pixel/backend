package clinica_juridica.backend.models;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("expediente_tribunales")
public class ExpedienteTribunal {
    @Id
    @Column("num_expediente")
    private String numExpediente;

    @Column("fecha_creacion")
    private LocalDate fechaCreacion;

    @Column("id_tribunal")
    private Integer idTribunal;

    @Column("id_caso")
    private String idCaso;

    public ExpedienteTribunal() {
    }

    public ExpedienteTribunal(String numExpediente, LocalDate fechaCreacion, Integer idTribunal, String idCaso) {
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
