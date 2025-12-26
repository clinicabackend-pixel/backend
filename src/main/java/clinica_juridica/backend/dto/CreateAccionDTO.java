package clinica_juridica.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class CreateAccionDTO {
    private String titulo;
    private String descripcion;
    private LocalDate fechaRegistro;
    private LocalDate fechaEjecucion;
    private String username; // The one who registers
    private List<String> ejecutantes; // List of usernames who executed it

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

    public List<String> getEjecutantes() {
        return ejecutantes;
    }

    public void setEjecutantes(List<String> ejecutantes) {
        this.ejecutantes = ejecutantes;
    }
}
