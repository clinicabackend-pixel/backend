package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("municipios")
public class Municipio {
    @Id
    @Column("id_municipio")
    private Integer idMunicipio;

    @Column("id_estado")
    private Integer idEstado;

    @Column("nombre_municipio")
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
