package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("parroquias")
public class Parroquia {
    @Id
    private Integer idParroquia;
    @Column("nombre_parroquia")
    private String nombreParroquia;
    private Integer idMunicipio;

    public Parroquia() {
    }

    public Parroquia(Integer idParroquia, String nombreParroquia, Integer idMunicipio) {
        this.idParroquia = idParroquia;
        this.nombreParroquia = nombreParroquia;
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(Integer idParroquia) {
        this.idParroquia = idParroquia;
    }

    public String getNombreParroquia() {
        return nombreParroquia;
    }

    public void setNombreParroquia(String nombreParroquia) {
        this.nombreParroquia = nombreParroquia;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    @Override
    public String toString() {
        return "Parroquia{idParroquia=" + idParroquia + ", nombreParroquia='" + nombreParroquia + "', idMunicipio="
                + idMunicipio
                + "}";
    }
}
