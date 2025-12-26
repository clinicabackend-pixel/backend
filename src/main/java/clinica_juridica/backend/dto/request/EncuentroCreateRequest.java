package clinica_juridica.backend.dto.request;

import java.time.LocalDate;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para registrar un nuevo encuentro")
public class EncuentroCreateRequest {
    @Schema(description = "Fecha de atención", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaAtencion;
    @Schema(description = "Fecha de la próxima cita")
    private LocalDate fechaProxima;
    @Schema(description = "Orientación brindada")
    private String orientacion;
    @Schema(description = "Observaciones adicionales")
    private String observacion;
    @Schema(description = "Usuario responsable")
    private String username; // The one who registers
    @Schema(description = "Lista de usuarios atendidos")
    private List<String> atendidos; // List of usernames who attended

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public LocalDate getFechaProxima() {
        return fechaProxima;
    }

    public void setFechaProxima(LocalDate fechaProxima) {
        this.fechaProxima = fechaProxima;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getAtendidos() {
        return atendidos;
    }

    public void setAtendidos(List<String> atendidos) {
        this.atendidos = atendidos;
    }
}
