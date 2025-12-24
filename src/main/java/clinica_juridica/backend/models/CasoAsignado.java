package clinica_juridica.backend.models;

import org.springframework.data.relational.core.mapping.Table;

@Table("casos_asignados")
public class CasoAsignado {
    // Composite PK: num_caso, username, termino
    private String numCaso;
    private String username; // Estudiante
    private String termino;

    public CasoAsignado() {
    }

    public CasoAsignado(String numCaso, String username, String termino) {
        this.numCaso = numCaso;
        this.username = username;
        this.termino = termino;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
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
        return "CasoAsignado{numCaso='" + numCaso + "', username='" + username + "', termino='" + termino + "'}";
    }
}
