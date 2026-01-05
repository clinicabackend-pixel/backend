package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("viviendas")
public class Vivienda implements Persistable<String> {
    @Id
    @Column("cedula")
    private String cedula;

    @Column("cant_habit")
    private Integer cantHabitaciones;

    @Column("cant_banos")
    private Integer cantBanos;

    @Transient
    private boolean isNew = false;

    public Vivienda() {
    }

    public Vivienda(String cedula, Integer cantHabitaciones, Integer cantBanos) {
        this.cedula = cedula;
        this.cantHabitaciones = cantHabitaciones;
        this.cantBanos = cantBanos;
        this.isNew = true;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public String getId() {
        return cedula;
    }

    @Override
    public boolean isNew() {
        return isNew; // Simplification, logic handled in service usually
    }

    @Override
    public String toString() {
        return "Vivienda{cedula='" + cedula + "', cantHabitaciones=" + cantHabitaciones + ", cantBanos=" + cantBanos
                + "}";
    }
}
