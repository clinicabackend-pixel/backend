package clinica_juridica.backend.domain.entities;

import java.time.LocalDate;

public class Caso {

    public enum EstadoCaso {
        ABIERTO,
        CERRADO,
        ACTIVO,
        EN_PAUSA
    }

    private String numCaso;
    private LocalDate fechaRecepcion;
    private Integer cantBeneficiarios;
    private String tramite;
    private String estatus;
    private String sintesis;
    private Integer idCentro;
    private Integer idAmbitoLegal;
    private String idSolicitante;

    public Caso() {
    }

    public Caso(String numCaso, LocalDate fechaRecepcion, Integer cantBeneficiarios, 
                String tramite, String estatus, String sintesis, Integer idCentro, 
                Integer idAmbitoLegal, String idSolicitante) {
        this.numCaso = numCaso;
        this.fechaRecepcion = fechaRecepcion;
        this.cantBeneficiarios = cantBeneficiarios;
        this.tramite = tramite;
        this.estatus = estatus;
        this.sintesis = sintesis;
        this.idCentro = idCentro;
        this.idAmbitoLegal = idAmbitoLegal;
        this.idSolicitante = idSolicitante;
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

    public Integer getCantBeneficiarios() {
        return cantBeneficiarios;
    }

    public void setCantBeneficiarios(Integer cantBeneficiarios) {
        this.cantBeneficiarios = cantBeneficiarios;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getSintesis() {
        return sintesis;
    }

    public void setSintesis(String sintesis) {
        this.sintesis = sintesis;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public Integer getIdAmbitoLegal() {
        return idAmbitoLegal;
    }

    public void setIdAmbitoLegal(Integer idAmbitoLegal) {
        this.idAmbitoLegal = idAmbitoLegal;
    }

    public String getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
    }
}
