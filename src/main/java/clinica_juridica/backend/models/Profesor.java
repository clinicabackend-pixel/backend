package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("profesores")
public class Profesor {
    @Id
    private String username;
    private String termino;

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
    public String toString() {
        return "Profesor{username='" + username + "', termino='" + termino + "'}";
    }
}
