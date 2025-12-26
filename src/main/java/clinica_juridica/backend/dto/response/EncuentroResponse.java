package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Respuesta de un encuentro programado")
public class EncuentroResponse {
    @Schema(description = "ID del encuentro")
    private Integer idEncuentro;
    @Schema(description = "Número de caso asociado")
    private String numCaso;
    @Schema(description = "Fecha de atención")
    private LocalDate fechaAtencion;
    @Schema(description = "Fecha de próxima cita")
    private LocalDate fechaProxima;
    @Schema(description = "Orientación brindada")
    private String orientacion;
    @Schema(description = "Observaciones")
    private String observacion;
    @Schema(description = "Usuario responsable")
    private String username;

    public EncuentroResponse() {}

    public EncuentroResponse(Integer idEncuentro, String numCaso, LocalDate fechaAtencion, LocalDate fechaProxima, String orientacion, String observacion, String username) {
        this.idEncuentro = idEncuentro;
        this.numCaso = numCaso;
        this.fechaAtencion = fechaAtencion;
        this.fechaProxima = fechaProxima;
        this.orientacion = orientacion;
        this.observacion = observacion;
        this.username = username;
    }

    public Integer getIdEncuentro() { return idEncuentro; }
    public void setIdEncuentro(Integer idEncuentro) { this.idEncuentro = idEncuentro; }

    public String getNumCaso() { return numCaso; }
    public void setNumCaso(String numCaso) { this.numCaso = numCaso; }

    public LocalDate getFechaAtencion() { return fechaAtencion; }
    public void setFechaAtencion(LocalDate fechaAtencion) { this.fechaAtencion = fechaAtencion; }

    public LocalDate getFechaProxima() { return fechaProxima; }
    public void setFechaProxima(LocalDate fechaProxima) { this.fechaProxima = fechaProxima; }

    public String getOrientacion() { return orientacion; }
    public void setOrientacion(String orientacion) { this.orientacion = orientacion; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
