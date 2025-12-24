package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("acciones_ejecutadas")
public class AccionEjecutada {
    @Id
    private Integer idAccionEjecutada;
    private Integer idAccion;
    private String numCaso;
    private String username;

    public AccionEjecutada() {
    }

    public AccionEjecutada(Integer idAccionEjecutada, Integer idAccion, String numCaso, String username) {
        this.idAccionEjecutada = idAccionEjecutada;
        this.idAccion = idAccion;
        this.numCaso = numCaso;
        this.username = username;
    }

    public Integer getIdAccionEjecutada() {
        return idAccionEjecutada;
    }

    public void setIdAccionEjecutada(Integer idAccionEjecutada) {
        this.idAccionEjecutada = idAccionEjecutada;
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
        return "AccionEjecutada{idAccionEjecutada=" + idAccionEjecutada + "}";
    }
}
