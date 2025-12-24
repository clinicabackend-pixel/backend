package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("parroquias")
public class Parroquia {
    @Id
    private Integer idParroquia;
    private String parroquia;
    private Integer idMunicipio;

    public Parroquia() {
    }

    public Parroquia(Integer idParroquia, String parroquia, Integer idMunicipio) {
        this.idParroquia = idParroquia;
        this.parroquia = parroquia;
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(Integer idParroquia) {
        this.idParroquia = idParroquia;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    @Override
    public String toString() {
        return "Parroquia{idParroquia=" + idParroquia + ", parroquia='" + parroquia + "', idMunicipio=" + idMunicipio
                + "}";
    }
}
