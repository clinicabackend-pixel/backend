package clinica_juridica.backend.dto.response;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resumen ligero de un caso legal (sin relaciones pesadas)")
public class CasoSummaryResponse {
    @Schema(description = "Número de caso")
    private String numCaso;
    @Schema(description = "Fecha de recepción")
    private LocalDate fechaRecepcion;
    @Schema(description = "Síntesis o resumen breve")
    private String sintesis;
    @Schema(description = "Estatus actual")
    private String estatus;
    @Schema(description = "Usuario asignado")
    private String username;
    @Schema(description = "Término o semestre")
    private String termino;
    @Schema(description = "Cédula del solicitante")
    private String cedula;
    @Schema(description = "Nombre del solicitante")
    private String nombreSolicitante;
    @Schema(description = "ID del ámbito legal")
    private Integer comAmbLegal;
    @Schema(description = "Jerarquía legal completa")
    private String legalHierarchy;

    public CasoSummaryResponse() {
    }

    public CasoSummaryResponse(String numCaso, LocalDate fechaRecepcion, String sintesis, String estatus,
            String username, String termino, String cedula, String nombreSolicitante, Integer comAmbLegal,
            String legalHierarchy) {
        this.numCaso = numCaso;
        this.fechaRecepcion = fechaRecepcion;
        this.sintesis = sintesis;
        this.estatus = estatus;
        this.username = username;
        this.termino = termino;
        this.cedula = cedula;
        this.nombreSolicitante = nombreSolicitante;
        this.comAmbLegal = comAmbLegal;
        this.legalHierarchy = legalHierarchy;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getSintesis() {
        return sintesis;
    }

    public void setSintesis(String sintesis) {
        this.sintesis = sintesis;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public Integer getComAmbLegal() {
        return comAmbLegal;
    }

    public void setComAmbLegal(Integer comAmbLegal) {
        this.comAmbLegal = comAmbLegal;
    }

    public String getLegalHierarchy() {
        return legalHierarchy;
    }

    public void setLegalHierarchy(String legalHierarchy) {
        this.legalHierarchy = legalHierarchy;
    }
}
