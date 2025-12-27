package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("niveles_educativos")
public class NivelEducativo {
    @Id
    @Column("id_nivel_edu")
    private Integer idNivelEdu;

    @Column("nivel")
    private String nivel;


    @Column("estatus")
    private String estatus;

    public NivelEducativo() {
    }

    public NivelEducativo(Integer idNivelEdu, String nivel, String estatus) {
        this.idNivelEdu = idNivelEdu;
        this.nivel = nivel;
        this.estatus = estatus;
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


    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
