package clinica_juridica.backend.models;

import org.springframework.data.relational.core.mapping.Table;

@Table("acciones_ejecutadas")
public class AccionEjecutada {
    private Integer idAccion;
    private String numCaso;
    private String username;

    public AccionEjecutada() {
    }

    public AccionEjecutada(Integer idAccion, String numCaso, String username) {
        this.idAccion = idAccion;
        this.numCaso = numCaso;
        this.username = username;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
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

    @Override
    public String toString() {
        return "AccionEjecutada{idAccion=" + idAccion + ", numCaso='" + numCaso + "', username='" + username + "'}";
    }
}
