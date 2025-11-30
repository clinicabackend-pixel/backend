package clinica_juridica.backend.domain.models;

import java.math.BigDecimal;

public class Familia {
    private String idSolicitante;
    private BigDecimal ingresosMes;
    private Integer cantPersonas;
    private Boolean jefeFamilia;
    private Integer cantSinTrabajo;
    private Integer cantEstudiando;
    private Integer cantNinos;
    private Integer cantTrabaja;
    private Integer idNivelEdu;

    public Familia() {
    }

    public Familia(String idSolicitante, BigDecimal ingresosMes, Integer cantPersonas, 
                   Boolean jefeFamilia, Integer cantSinTrabajo, Integer cantEstudiando, 
                   Integer cantNinos, Integer cantTrabaja, Integer idNivelEdu) {
        this.idSolicitante = idSolicitante;
        this.ingresosMes = ingresosMes;
        this.cantPersonas = cantPersonas;
        this.jefeFamilia = jefeFamilia;
        this.cantSinTrabajo = cantSinTrabajo;
        this.cantEstudiando = cantEstudiando;
        this.cantNinos = cantNinos;
        this.cantTrabaja = cantTrabaja;
        this.idNivelEdu = idNivelEdu;
    }

    public String getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public BigDecimal getIngresosMes() {
        return ingresosMes;
    }

    public void setIngresosMes(BigDecimal ingresosMes) {
        this.ingresosMes = ingresosMes;
    }

    public Integer getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(Integer cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    public Boolean getJefeFamilia() {
        return jefeFamilia;
    }

    public void setJefeFamilia(Boolean jefeFamilia) {
        this.jefeFamilia = jefeFamilia;
    }

    public Integer getCantSinTrabajo() {
        return cantSinTrabajo;
    }

    public void setCantSinTrabajo(Integer cantSinTrabajo) {
        this.cantSinTrabajo = cantSinTrabajo;
    }

    public Integer getCantEstudiando() {
        return cantEstudiando;
    }

    public void setCantEstudiando(Integer cantEstudiando) {
        this.cantEstudiando = cantEstudiando;
    }

    public Integer getCantNinos() {
        return cantNinos;
    }

    public void setCantNinos(Integer cantNinos) {
        this.cantNinos = cantNinos;
    }

    public Integer getCantTrabaja() {
        return cantTrabaja;
    }

    public void setCantTrabaja(Integer cantTrabaja) {
        this.cantTrabaja = cantTrabaja;
    }

    public Integer getIdNivelEdu() {
        return idNivelEdu;
    }

    public void setIdNivelEdu(Integer idNivelEdu) {
        this.idNivelEdu = idNivelEdu;
    }
}

