package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("encuentros_atendidos")
public class EncuentroAtendido {
    @Id
    private Integer idEncuentroAtendido;
    private Integer idEncuentro;
    private String numCaso;
    private String username;

    public EncuentroAtendido() {
    }

    public EncuentroAtendido(Integer idEncuentroAtendido, Integer idEncuentro, String numCaso, String username) {
        this.idEncuentroAtendido = idEncuentroAtendido;
        this.idEncuentro = idEncuentro;
        this.numCaso = numCaso;
        this.username = username;
    }

    public Integer getIdEncuentroAtendido() {
        return idEncuentroAtendido;
    }

    public void setIdEncuentroAtendido(Integer idEncuentroAtendido) {
        this.idEncuentroAtendido = idEncuentroAtendido;
    }

    public Integer getIdEncuentro() {
        return idEncuentro;
    }

    public void setIdEncuentro(Integer idEncuentro) {
        this.idEncuentro = idEncuentro;
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
        return "EncuentroAtendido{idEncuentroAtendido=" + idEncuentroAtendido + "}";
    }
}
