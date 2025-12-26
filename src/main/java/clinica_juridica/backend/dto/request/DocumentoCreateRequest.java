package clinica_juridica.backend.dto.request;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para registrar un nuevo documento")
public class DocumentoCreateRequest {
    @Schema(description = "Fecha de registro")
    private LocalDate fechaRegistro;
    @Schema(description = "Folio inicial")
    private Integer folioIni;
    @Schema(description = "Folio final")
    private Integer folioFin;
    @Schema(description = "Título del documento", requiredMode = Schema.RequiredMode.REQUIRED)
    private String titulo;
    @Schema(description = "Observaciones adicionales")
    private String observacion;
    @Schema(description = "Usuario que registra")
    private String username;

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getFolioIni() {
        return folioIni;
    }

    public void setFolioIni(Integer folioIni) {
        this.folioIni = folioIni;
    }

    public Integer getFolioFin() {
        return folioFin;
    }

    public void setFolioFin(Integer folioFin) {
        this.folioFin = folioFin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
}
