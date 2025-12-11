package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Electrodomesticos")
public class Electrodomestico {
    @Id
    @Column("id_electrodomestico")
    private Integer idElectrodomestico;

    @Column("nombre_electrodomestico")
    private String nombreElectrodomestico;

    public Electrodomestico() {
    }

    public Electrodomestico(Integer idElectrodomestico, String nombreElectrodomestico) {
        this.idElectrodomestico = idElectrodomestico;
        this.nombreElectrodomestico = nombreElectrodomestico;
    }

    public Integer getIdElectrodomestico() {
        return idElectrodomestico;
    }

    public void setIdElectrodomestico(Integer idElectrodomestico) {
        this.idElectrodomestico = idElectrodomestico;
    }

    public String getNombreElectrodomestico() {
        return nombreElectrodomestico;
    }

    public void setNombreElectrodomestico(String nombreElectrodomestico) {
        this.nombreElectrodomestico = nombreElectrodomestico;
    }
}
