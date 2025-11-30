package clinica_juridica.backend.domain.models;

public class Estudiante {
    private String idEstudiante;
    private String nombre;
    private String sexo;
    private String email;
    private String username;
    private String contrasena;
    private String estatus;
    private Boolean culminado;
    private String tipo;

    public Estudiante() {
    }

    public Estudiante(String idEstudiante, String nombre, String sexo, String email, 
                      String username, String contrasena, String estatus, 
                      Boolean culminado, String tipo) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.sexo = sexo;
        this.email = email;
        this.username = username;
        this.contrasena = contrasena;
        this.estatus = estatus;
        this.culminado = culminado;
        this.tipo = tipo;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Boolean getCulminado() {
        return culminado;
    }

    public void setCulminado(Boolean culminado) {
        this.culminado = culminado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

