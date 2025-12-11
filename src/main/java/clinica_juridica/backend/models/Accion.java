package clinica_juridica.backend.models;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Acciones")
public class Accion {
    @Id
    @Column("id_accion")
    private Integer idAccion;

    @Column("num_caso")
    private String numCaso;

    @Column("titulo")
    private String titulo;

    @Column("descripcion")
    private String descripcion;

    @Column("id_usuario")
    private String idUsuario;

    @Column("f_registro")
    private LocalDate fRegistro;

    @Column("f_ejecucion")
    private LocalDate fEjecucion;

    public Accion() {
    }

    public Accion(Integer idAccion, String numCaso, String titulo, String descripcion,
            String idUsuario, LocalDate fRegistro, LocalDate fEjecucion) {
        this.idAccion = idAccion;
        this.numCaso = numCaso;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
        this.fRegistro = fRegistro;
        this.fEjecucion = fEjecucion;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFRegistro() {
        return fRegistro;
    }

    public void setFRegistro(LocalDate fRegistro) {
        this.fRegistro = fRegistro;
    }

    public LocalDate getFEjecucion() {
        return fEjecucion;
    }

    public void setFEjecucion(LocalDate fEjecucion) {
        this.fEjecucion = fEjecucion;
    }
}
