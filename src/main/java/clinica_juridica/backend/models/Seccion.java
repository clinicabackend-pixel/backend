package clinica_juridica.backend.models;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Secciones")
public class Seccion {
    @Column("id_materia")
    private Integer idMateria;

    @Column("id_seccion")
    private Integer idSeccion;

    @Column("id_semestre")
    private Integer idSemestre;

    @Column("id_profesor")
    private String idProfesor;

    @Column("id_coordinador")
    private String idCoordinador;

    public Seccion() {
    }

    public Seccion(Integer idMateria, Integer idSeccion, Integer idSemestre, String idProfesor, String idCoordinador) {
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
