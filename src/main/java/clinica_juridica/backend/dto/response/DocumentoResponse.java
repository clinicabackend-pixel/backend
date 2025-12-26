package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Respuesta de un documento registrado")
public class DocumentoResponse {
    @Schema(description = "ID del documento")
    private Integer idDocumento;
    @Schema(description = "Número de caso")
    private String numCaso;
    @Schema(description = "Fecha de registro")
    private LocalDate fechaRegistro;
    @Schema(description = "Folio inicial")
    private Integer folioIni;
    @Schema(description = "Folio final")
    private Integer folioFin;
    @Schema(description = "Título del documento")
    private String titulo;
    @Schema(description = "Observación")
    private String observacion;
    @Schema(description = "Usuario que registró")
    private String username;

    public DocumentoResponse() {
    }

    public DocumentoResponse(Integer idDocumento, String numCaso, LocalDate fechaRegistro, Integer folioIni,
            Integer folioFin, String titulo, String observacion, String username) {
        this.idDocumento = idDocumento;
        this.numCaso = numCaso;
        this.fechaRegistro = fechaRegistro;
        this.folioIni = folioIni;
        this.folioFin = folioFin;
        this.titulo = titulo;
        this.observacion = observacion;
        this.username = username;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

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
