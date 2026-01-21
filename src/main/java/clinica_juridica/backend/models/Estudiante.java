package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("estudiantes")
public class Estudiante implements org.springframework.data.domain.Persistable<String> {
    @Id
    private String username; // Part of composite key
    private String termino; // Part of composite key
    private String tipoDeEstudiante;
    private Integer nrc;

    @org.springframework.data.annotation.Transient
    private boolean isNew = false;

    public Estudiante() {
    }

    public Estudiante(String username, String termino, String tipoDeEstudiante, Integer nrc) {
        this.username = username;
        this.termino = termino;
        this.tipoDeEstudiante = tipoDeEstudiante;
        this.nrc = nrc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getTipoDeEstudiante() {
        return tipoDeEstudiante;
    }

    public void setTipoDeEstudiante(String tipoDeEstudiante) {
        this.tipoDeEstudiante = tipoDeEstudiante;
    }

    public Integer getNrc() {
        return nrc;
    }

    public void setNrc(Integer nrc) {
        this.nrc = nrc;
    }

    @Override
    public String getId() {
        return username;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public String toString() {
        return "Estudiante{username='" + username + "', termino='" + termino + "', tipoDeEstudiante='"
                + tipoDeEstudiante + "', nrc=" + nrc + "}";
    }
}
