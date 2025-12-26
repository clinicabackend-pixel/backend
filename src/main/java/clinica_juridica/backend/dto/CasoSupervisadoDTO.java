package clinica_juridica.backend.dto;

public class CasoSupervisadoDTO {
    private String numCaso;
    private String username;
    private String termino;
    private String nombre; // Nombre del usuario

    public CasoSupervisadoDTO(String numCaso, String username, String termino, String nombre) {
        this.numCaso = numCaso;
        this.username = username;
        this.termino = termino;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
