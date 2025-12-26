package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("encuentros")
@Schema(description = "Entidad que representa un encuentro o cita en un caso")
public class Encuentro {
    @Id
    @Schema(description = "Identificador único del encuentro")
    private Integer idEncuentros;
    @Schema(description = "Número del caso asociado")
    private String numCaso;
    @Schema(description = "Fecha de atención")
    private LocalDate fechaAtencion;
    @Schema(description = "Fecha de la próxima cita")
    private LocalDate fechaProxima;
    @Schema(description = "Orientación brindada")
    private String orientacion;
    @Schema(description = "Observaciones adicionales")
    private String observacion;
    @Schema(description = "Usuario responsable del encuentro")
    private String username;

    public Encuentro() {
    }

    public Encuentro(Integer idEncuentros, String numCaso, LocalDate fechaAtencion, LocalDate fechaProxima,
            String orientacion, String observacion, String username) {
        this.idEncuentros = idEncuentros;
        this.numCaso = numCaso;
        this.fechaAtencion = fechaAtencion;
        this.fechaProxima = fechaProxima;
        this.orientacion = orientacion;
        this.observacion = observacion;
        this.username = username;
    }

    public Integer getIdEncuentros() {
        return idEncuentros;
    }

    public void setIdEncuentros(Integer idEncuentros) {
        this.idEncuentros = idEncuentros;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

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

    @Override
    public String toString() {
        return "Encuentro{idEncuentros=" + idEncuentros + ", numCaso='" + numCaso + "'}";
    }
}
