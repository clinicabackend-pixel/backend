package clinica_juridica.backend.domain.models;

public class Municipio {
    private Integer idMunicipio;
    private Integer idEstado;
    private String nombreMunicipio;

    public Municipio() {
    }

    public Municipio(Integer idMunicipio, Integer idEstado, String nombreMunicipio) {
        this.idMunicipio = idMunicipio;
        this.idEstado = idEstado;
        this.nombreMunicipio = nombreMunicipio;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }
}

