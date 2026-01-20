package clinica_juridica.backend.dto.request;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para registrar una nueva prueba")
public class PruebaCreateRequest {
    @Schema(description = "Fecha de la prueba")
    private LocalDate fecha;
    @Schema(description = "Nombre o descripción del documento probatorio")
    private String documento;
    @Schema(description = "Observaciones adicionales")
    private String observacion;
    @Schema(description = "Título de la prueba", requiredMode = Schema.RequiredMode.REQUIRED)
    private String titulo;

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Schema(description = "Nombre de usuario que sube la prueba")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
