package clinica_juridica.backend.domain.models;

public class Seccion {
    private Integer idMateria;
    private Integer idSeccion;
    private Integer idSemestre;
    private String idProfesor;
    private String idCoordinador;

    public Seccion() {
    }

    public Seccion(Integer idMateria, Integer idSeccion, Integer idSemestre, 
                   String idProfesor, String idCoordinador) {
        this.idMateria = idMateria;
        this.idSeccion = idSeccion;
        this.idSemestre = idSemestre;
        this.idProfesor = idProfesor;
        this.idCoordinador = idCoordinador;
    }

    public Integer getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public Integer getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Integer idSemestre) {
        this.idSemestre = idSemestre;
    }

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(String idCoordinador) {
        this.idCoordinador = idCoordinador;
    }
}

