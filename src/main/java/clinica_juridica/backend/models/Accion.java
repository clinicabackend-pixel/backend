package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("accion")
public class Accion {
    @Id
    private Integer idAccion;
    private String numCaso;
    private String titulo;
    private String descripcion;
    private LocalDate fechaRegistro;
    private LocalDate fechaEjecucion;
    private String username;

    public Accion() {
    }

    public Accion(Integer idAccion, String numCaso, String titulo, String descripcion, LocalDate fechaRegistro,
            LocalDate fechaEjecucion, String username) {
        this.idAccion = idAccion;
        this.numCaso = numCaso;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
        this.fechaEjecucion = fechaEjecucion;
        this.username = username;
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

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDate fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Accion{idAccion=" + idAccion + ", titulo='" + titulo + "'}";
    }
}
