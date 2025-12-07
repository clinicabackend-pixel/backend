package clinica_juridica.backend.domain.models;

public class AmbitoLegal {
    private Integer idAmbitoLegal;
    private String materia;
    private String tipo;
    private String descripcion;

    public AmbitoLegal() {
    }

    public AmbitoLegal(Integer idAmbitoLegal, String materia, String tipo, String descripcion) {
        this.idAmbitoLegal = idAmbitoLegal;
        this.materia = materia;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Integer getIdAmbitoLegal() {
        return idAmbitoLegal;
    }

    public void setIdAmbitoLegal(Integer idAmbitoLegal) {
        this.idAmbitoLegal = idAmbitoLegal;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
