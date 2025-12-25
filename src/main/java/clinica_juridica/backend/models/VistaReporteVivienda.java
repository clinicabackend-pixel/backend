package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("vista_reporte_vivienda")
public class VistaReporteVivienda {
    @Id
    private String cedula;
    private String nombre;
    private String tipoVivienda;
    private String materialPiso;
    private String materialParedes;
    private String materialTecho;
    private String servicioAgua;
    private String eliminacionExcretas;
    private String aseoUrbano;
    private Integer cantHabit;
    private Integer cantBanos;

    public VistaReporteVivienda() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getMaterialPiso() {
        return materialPiso;
    }

    public void setMaterialPiso(String materialPiso) {
        this.materialPiso = materialPiso;
    }

    public String getMaterialParedes() {
        return materialParedes;
    }

    public void setMaterialParedes(String materialParedes) {
        this.materialParedes = materialParedes;
    }

    public String getMaterialTecho() {
        return materialTecho;
    }

    public void setMaterialTecho(String materialTecho) {
        this.materialTecho = materialTecho;
    }

    public String getServicioAgua() {
        return servicioAgua;
    }

    public void setServicioAgua(String servicioAgua) {
        this.servicioAgua = servicioAgua;
    }

    public String getEliminacionExcretas() {
        return eliminacionExcretas;
    }

    public void setEliminacionExcretas(String eliminacionExcretas) {
        this.eliminacionExcretas = eliminacionExcretas;
    }

    public String getAseoUrbano() {
        return aseoUrbano;
    }

    public void setAseoUrbano(String aseoUrbano) {
        this.aseoUrbano = aseoUrbano;
    }

    public Integer getCantHabit() {
        return cantHabit;
    }

    public void setCantHabit(Integer cantHabit) {
        this.cantHabit = cantHabit;
    }

    public Integer getCantBanos() {
        return cantBanos;
    }

    public void setCantBanos(Integer cantBanos) {
        this.cantBanos = cantBanos;
    }
}
