package clinica_juridica.backend.models;

import java.time.LocalDateTime;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Citas_atendidas")
public class CitaAtendida {
    @Column("num_caso")
    private String numCaso;

    @Column("fecha")
    private LocalDateTime fecha;

    @Column("id_usuario")
    private String idUsuario;

    public CitaAtendida() {
    }

    public CitaAtendida(String numCaso, LocalDateTime fecha, String idUsuario) {
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
