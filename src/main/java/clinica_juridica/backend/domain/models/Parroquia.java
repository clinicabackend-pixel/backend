package clinica_juridica.backend.domain.models;

public class Parroquia {
    private Integer idParroquia;
    private Integer idMunicipio;
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

