package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("vista_reporte_vivienda")
public class VistaReporteVivienda {
    @Id
    @Column("cedula")
    private String cedula;

    @Column("nombre")
    private String nombre;

    @Column("tipo_vivienda")
    private String tipoVivienda;

    @Column("material_piso")
    private String materialPiso;

    @Column("material_paredes")
    private String materialParedes;

    @Column("material_techo")
    private String materialTecho;

    @Column("servicio_agua")
    private String servicioAgua;

    @Column("eliminacion_excretas")
    private String eliminacionExcretas;

    @Column("aseo_urbano")
    private String aseoUrbano;

    @Column("cant_habit")
    private Integer cantHabit;

    @Column("cant_banos")
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
