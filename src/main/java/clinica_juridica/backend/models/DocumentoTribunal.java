package clinica_juridica.backend.models;

import java.time.LocalDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Documentos_tribunales")
public class DocumentoTribunal {
    @Column("num_expediente")
    private String numExpediente;

    @Column("folio_ini")
    private Integer folioIni;

    @Column("folio_fin")
    private Integer folioFin;

    @Column("fecha")
    private LocalDate fecha;

    @Column("titulo")
    private String titulo;

    @Column("descripcion")
    private String descripcion;

    public DocumentoTribunal() {
    }

    public DocumentoTribunal(String numExpediente, Integer folioIni, Integer folioFin, LocalDate fecha, String titulo,
            String descripcion) {
        this.numExpediente = numExpediente;
        this.folioIni = folioIni;
        this.folioFin = folioFin;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getNumExpediente() {
        return numExpediente;
    }

    public void setNumExpediente(String numExpediente) {
        this.numExpediente = numExpediente;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
