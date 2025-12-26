package clinica_juridica.backend.models;


import org.springframework.data.relational.core.mapping.Table;

@Table("encuentros_atendidos")
public class EncuentroAtendido {
    private Integer idEncuentro;
    private String numCaso;
    private String username;

    public EncuentroAtendido() {
    }

    public EncuentroAtendido(Integer idEncuentro, String numCaso, String username) {
        this.idEncuentro = idEncuentro;
        this.numCaso = numCaso;
        this.username = username;
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
        return "EncuentroAtendido{idEncuentro=" + idEncuentro + ", numCaso='" + numCaso + "', username='" + username
                + "'}";
    }
}
