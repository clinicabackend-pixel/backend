package clinica_juridica.backend.domain.entities;

import java.time.LocalDate;

public class Asignacion {
    private String idEstudiante;
    private String numCaso;
    private Integer idSemestre;
    private String idProfesor;
    private String tipoEstudiante;
    private LocalDate fechaAsignacion;

    public Asignacion() {
    }

    public Asignacion(String idEstudiante, String numCaso, Integer idSemestre, 
                      String idProfesor, String tipoEstudiante, LocalDate fechaAsignacion) {
        this.idEstudiante = idEstudiante;
        this.numCaso = numCaso;
        this.idSemestre = idSemestre;
        this.idProfesor = idProfesor;
        this.tipoEstudiante = tipoEstudiante;
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
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

    public String getTipoEstudiante() {
        return tipoEstudiante;
    }

    public void setTipoEstudiante(String tipoEstudiante) {
        this.tipoEstudiante = tipoEstudiante;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}

