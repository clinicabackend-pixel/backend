package clinica_juridica.backend.domain.entities;

public class EstudianteInscrito {
    private String idEstudiante;
    private Integer idMateria;
    private Integer idSemestre;
    private Integer idSeccion;

    public EstudianteInscrito() {
    }

    public EstudianteInscrito(String idEstudiante, Integer idMateria, 
                              Integer idSemestre, Integer idSeccion) {
        this.idEstudiante = idEstudiante;
        this.idMateria = idMateria;
        this.idSemestre = idSemestre;
        this.idSeccion = idSeccion;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Integer getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    public Integer getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Integer idSemestre) {
        this.idSemestre = idSemestre;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }
}

