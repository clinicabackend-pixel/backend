package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("profesores")
public class Profesor implements org.springframework.data.domain.Persistable<String> {
    @Id
    private String username;
    private String termino;

    @org.springframework.data.annotation.Transient
    private boolean isNew = false;

    public Profesor() {
    }

    public Profesor(String username, String termino) {
        this.username = username;
        this.termino = termino;
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
        return "Profesor{username='" + username + "', termino='" + termino + "'}";
    }
}
