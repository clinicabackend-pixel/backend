package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("coordinadores")
public class Coordinador {
    @Id
    private String username;

    public Coordinador() {
    }

    public Coordinador(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Coordinador{username='" + username + "'}";
    }
}
