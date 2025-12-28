package clinica_juridica.backend.dto.request;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la actualización de una acción (Lista Blanca)")
public class AccionUpdateRequest {

    @Schema(description = "Título de la acción")
    private String titulo;

    @Schema(description = "Descripción detallada de la acción")
    private String descripcion;

    @Schema(description = "Fecha de ejecución de la acción")
    private LocalDate fechaEjecucion;

    @Schema(description = "Lista de usuarios que ejecutaron la acción")
    private java.util.List<String> usernames;

    public AccionUpdateRequest() {
    }

    public AccionUpdateRequest(String titulo, String descripcion, LocalDate fechaEjecucion,
            java.util.List<String> usernames) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaEjecucion = fechaEjecucion;
        this.usernames = usernames;
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

    public LocalDate getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDate fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public java.util.List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(java.util.List<String> usernames) {
        this.usernames = usernames;
    }
}
