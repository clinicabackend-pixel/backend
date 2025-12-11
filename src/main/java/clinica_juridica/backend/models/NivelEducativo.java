package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Niveles_Educativos")
public class NivelEducativo {
    @Id
    @Column("id_nivel_edu")
    private Integer idNivelEdu;

    @Column("nivel")
    private String nivel;

    @Column("anio")
    private Integer anio;

    public NivelEducativo() {
    }

    public NivelEducativo(Integer idNivelEdu, String nivel, Integer anio) {
        this.idNivelEdu = idNivelEdu;
        this.nivel = nivel;
        this.anio = anio;
    }

    public Integer getIdNivelEdu() {
        return idNivelEdu;
    }

    public void setIdNivelEdu(Integer idNivelEdu) {
        this.idNivelEdu = idNivelEdu;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
}
