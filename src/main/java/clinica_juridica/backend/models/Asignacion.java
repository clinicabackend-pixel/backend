package clinica_juridica.backend.models;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("asignaciones")
public class Asignacion {
    @Id
    @Column("id_asignacion")
    private Integer idAsignacion;

    @Column("id_estudiante")
    private String idEstudiante;

    @Column("num_caso")
    private String numCaso;

    @Column("id_semestre")
    private Integer idSemestre;

    @Column("id_profesor")
    private String idProfesor;

    @Column("tipo_estudiante")
    private String tipoEstudiante;

    @Column("fecha_asignacion")
    private LocalDate fechaAsignacion;

    @Column("fecha_fin_asignacion")
    private LocalDate fechaFinAsignacion;

    @Column("id_materia")
    private Integer idMateria;

    @Column("id_seccion")
    private Integer idSeccion;

    public Asignacion() {
    }

    public Asignacion(Integer idAsignacion, String idEstudiante, String numCaso, Integer idSemestre,
            String idProfesor, String tipoEstudiante, LocalDate fechaAsignacion,
            LocalDate fechaFinAsignacion, Integer idMateria, Integer idSeccion) {
        this.idAsignacion = idAsignacion;
        this.idEstudiante = idEstudiante;
        this.numCaso = numCaso;
        this.idSemestre = idSemestre;
        this.idProfesor = idProfesor;
        this.tipoEstudiante = tipoEstudiante;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFinAsignacion = fechaFinAsignacion;
        this.idMateria = idMateria;
        this.idSeccion = idSeccion;
    }

    public Integer getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(Integer idAsignacion) {
        this.idAsignacion = idAsignacion;
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

    public LocalDate getFechaFinAsignacion() {
        return fechaFinAsignacion;
    }

    public void setFechaFinAsignacion(LocalDate fechaFinAsignacion) {
        this.fechaFinAsignacion = fechaFinAsignacion;
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
}
