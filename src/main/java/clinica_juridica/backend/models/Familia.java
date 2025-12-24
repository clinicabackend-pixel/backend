package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Table("familias")
public class Familia implements Persistable<String> {

    @Id
    private String cedula;
    private Integer cantPersonas;
    private Integer cantEstudiando;
    private BigDecimal ingresoMes;
    private Boolean jefeFamilia;
    private Integer cantSinTrabajo;
    private Integer cantNinos;
    private Integer cantTrabaja;
    private Integer idNivelEduJefe;
    private String tiempoEstudio;

    public Familia() {
    }

    public Familia(String cedula, Integer cantPersonas, Integer cantEstudiando, BigDecimal ingresoMes,
            Boolean jefeFamilia, Integer cantSinTrabajo, Integer cantNinos, Integer cantTrabaja, Integer idNivelEduJefe,
            String tiempoEstudio) {
        this.cedula = cedula;
        this.cantPersonas = cantPersonas;
        this.cantEstudiando = cantEstudiando;
        this.ingresoMes = ingresoMes;
        this.jefeFamilia = jefeFamilia;
        this.cantSinTrabajo = cantSinTrabajo;
        this.cantNinos = cantNinos;
        this.cantTrabaja = cantTrabaja;
        this.idNivelEduJefe = idNivelEduJefe;
        this.tiempoEstudio = tiempoEstudio;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Integer getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(Integer cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    public Integer getCantEstudiando() {
        return cantEstudiando;
    }

    public void setCantEstudiando(Integer cantEstudiando) {
        this.cantEstudiando = cantEstudiando;
    }

    public BigDecimal getIngresoMes() {
        return ingresoMes;
    }

    public void setIngresoMes(BigDecimal ingresoMes) {
        this.ingresoMes = ingresoMes;
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

    public Integer getIdNivelEduJefe() {
        return idNivelEduJefe;
    }

    public void setIdNivelEduJefe(Integer idNivelEduJefe) {
        this.idNivelEduJefe = idNivelEduJefe;
    }

    public String getTiempoEstudio() {
        return tiempoEstudio;
    }

    public void setTiempoEstudio(String tiempoEstudio) {
        this.tiempoEstudio = tiempoEstudio;
    }

    @Override
    public String getId() {
        return cedula;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Familia familia = (Familia) o;
        return Objects.equals(cedula, familia.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    @Override
    public String toString() {
        return "Familia{" +
                "cedula='" + cedula + '\'' +
                ", cantPersonas=" + cantPersonas +
                ", ingresoMes=" + ingresoMes +
                '}';
    }
}
