package clinica_juridica.backend.domain.entities;

public class Materia {
    private Integer nrc;
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

