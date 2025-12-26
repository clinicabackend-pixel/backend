package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Respuesta de una acción realizada en un caso")
public class AccionResponse {
    @Schema(description = "ID de la acción")
    private Integer idAccion;
    @Schema(description = "Número de caso asociado")
    private String numCaso;
    @Schema(description = "Título de la acción")
    private String titulo;
    @Schema(description = "Descripción detallada")
    private String descripcion;
    @Schema(description = "Fecha de registro")
    private LocalDate fechaRegistro;
    @Schema(description = "Fecha de ejecución")
    private LocalDate fechaEjecucion;
    @Schema(description = "Usuario que registró la acción")
    private String username;

    public AccionResponse() {
    }

    public AccionResponse(Integer idAccion, String numCaso, String titulo, String descripcion, LocalDate fechaRegistro,
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
}
