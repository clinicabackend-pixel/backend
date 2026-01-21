package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("coordinadores")
public class Coordinador implements org.springframework.data.domain.Persistable<String> {
    @Id
    private String username;

    @org.springframework.data.annotation.Transient
    private boolean isNew = false;

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
        return "Coordinador{username='" + username + "'}";
    }
}
