package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Centros")
public class Centro {
    @Id
    @Column("id_centro")
    private Integer idCentro;

    @Column("nombre")
    private String nombre;

    @Column("id_parroquia")
    private Integer idParroquia;

    public Centro() {
    }

    public Centro(Integer idCentro, String nombre, Integer idParroquia) {
        this.idCentro = idCentro;
        this.nombre = nombre;
        this.idParroquia = idParroquia;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(Integer idParroquia) {
        this.idParroquia = idParroquia;
    }
}
