package clinica_juridica.backend.models;

import java.time.LocalDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Cambios_estatus")
public class CambioEstatus {
    @Column("id_usuario")
    private String idUsuario;

    @Column("num_caso")
    private String numCaso;

    @Column("fecha_cambio")
    private LocalDate fechaCambio;

    @Column("status_ant")
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
