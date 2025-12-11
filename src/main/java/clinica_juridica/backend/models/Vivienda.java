package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Viviendas")
public class Vivienda {
    @Id
    @Column("id_vivienda")
    private String idVivienda;

    @Column("tipo")
    private String tipo;

    @Column("cant_habitaciones")
    private Integer cantHabitaciones;

    @Column("cant_banos")
    private Integer cantBanos;

    @Column("material_paredes")
    private String materialParedes;

    @Column("aguas_negras")
    private String aguasNegras;

    @Column("servicio_agua")
    private String servicioAgua;

    @Column("material_techo")
    private String materialTecho;

    @Column("material_piso")
    private String materialPiso;

    @Column("servicio_aseo")
    private String servicioAseo;

    public Vivienda() {
    }

    public Vivienda(String idVivienda, String tipo, Integer cantHabitaciones,
            Integer cantBanos, String materialParedes, String aguasNegras,
            String servicioAgua, String materialTecho, String materialPiso,
            String servicioAseo) {
        this.idVivienda = idVivienda;
        this.tipo = tipo;
        this.cantHabitaciones = cantHabitaciones;
        this.cantBanos = cantBanos;
        this.materialParedes = materialParedes;
        this.aguasNegras = aguasNegras;
        this.servicioAgua = servicioAgua;
        this.materialTecho = materialTecho;
        this.materialPiso = materialPiso;
        this.servicioAseo = servicioAseo;
    }

    public String getIdVivienda() {
        return idVivienda;
    }

    public void setIdVivienda(String idVivienda) {
        this.idVivienda = idVivienda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCantHabitaciones() {
        return cantHabitaciones;
    }

    public void setCantHabitaciones(Integer cantHabitaciones) {
        this.cantHabitaciones = cantHabitaciones;
    }

    public Integer getCantBanos() {
        return cantBanos;
    }

    public void setCantBanos(Integer cantBanos) {
        this.cantBanos = cantBanos;
    }

    public String getMaterialParedes() {
        return materialParedes;
    }

    public void setMaterialParedes(String materialParedes) {
        this.materialParedes = materialParedes;
    }

    public String getAguasNegras() {
        return aguasNegras;
    }

    public void setAguasNegras(String aguasNegras) {
        this.aguasNegras = aguasNegras;
    }

    public String getServicioAgua() {
        return servicioAgua;
    }

    public void setServicioAgua(String servicioAgua) {
        this.servicioAgua = servicioAgua;
    }

    public String getMaterialTecho() {
        return materialTecho;
    }

    public void setMaterialTecho(String materialTecho) {
        this.materialTecho = materialTecho;
    }

    public String getMaterialPiso() {
        return materialPiso;
    }

    public void setMaterialPiso(String materialPiso) {
        this.materialPiso = materialPiso;
    }

    public String getServicioAseo() {
        return servicioAseo;
    }

    public void setServicioAseo(String servicioAseo) {
        this.servicioAseo = servicioAseo;
    }
}
