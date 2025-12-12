package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("parroquia")
public class Parroquia {
    @Id
    @Column("id_parroquia")
    private Integer idParroquia;

    @Column("id_municipio")
    private Integer idMunicipio;

    @Column("nombre_parroquia")
    private String nombreParroquia;

    public Parroquia() {
    }

    public Parroquia(Integer idParroquia, Integer idMunicipio, String nombreParroquia) {
        this.idParroquia = idParroquia;
        this.idMunicipio = idMunicipio;
        this.nombreParroquia = nombreParroquia;
    }

    public Integer getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(Integer idParroquia) {
        this.idParroquia = idParroquia;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombreParroquia() {
        return nombreParroquia;
    }

    public void setNombreParroquia(String nombreParroquia) {
        this.nombreParroquia = nombreParroquia;
    }
}
