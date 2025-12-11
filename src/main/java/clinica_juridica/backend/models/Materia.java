package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Materia")
public class Materia {
    @Id
    @Column("nrc")
    private Integer nrc;

    @Column("nombre_materia")
    private String nombreMateria;

    public Materia() {
    }

    public Materia(Integer nrc, String nombreMateria) {
        this.nrc = nrc;
        this.nombreMateria = nombreMateria;
    }

    public Integer getNrc() {
        return nrc;
    }

    public void setNrc(Integer nrc) {
        this.nrc = nrc;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
}
