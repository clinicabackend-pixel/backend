package clinica_juridica.backend.models;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Familias")
public class Familia {
    @Id
    @Column("id_solicitante")
    private String idSolicitante;

    @Column("ingresos_mes")
    private BigDecimal ingresosMes;

    @Column("cant_personas")
    private Integer cantPersonas;

    @Column("jefe_familia")
    private Boolean jefeFamilia;

    @Column("cant_sin_trabajo")
    private Integer cantSinTrabajo;

    @Column("cant_estudiando")
    private Integer cantEstudiando;

    @Column("cant_ninos")
    private Integer cantNinos;

    @Column("cant_trabaja")
    private Integer cantTrabaja;

    @Column("id_nivel_edu")
    private Integer idNivelEdu;

    public Familia() {
    }

    public Familia(String idSolicitante, BigDecimal ingresosMes, Integer cantPersonas, Boolean jefeFamilia,
            Integer cantSinTrabajo, Integer cantEstudiando, Integer cantNinos,
            Integer cantTrabaja, Integer idNivelEdu) {
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
