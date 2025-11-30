package clinica_juridica.backend.domain.models;

import java.time.LocalDate;

public class Accion {
    private Integer idAccion;
    private String numCaso;
    private String titulo;
    private String descripcion;
    private String idUsuario;
    private LocalDate fRegistro;
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

    public LocalDate getfRegistro() {
        return fRegistro;
    }

    public void setfRegistro(LocalDate fRegistro) {
        this.fRegistro = fRegistro;
    }

    public LocalDate getfEjecucion() {
        return fEjecucion;
    }

    public void setfEjecucion(LocalDate fEjecucion) {
        this.fEjecucion = fEjecucion;
    }
}

