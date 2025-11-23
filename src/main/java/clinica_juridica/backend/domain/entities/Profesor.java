package clinica_juridica.backend.domain.entities;

public class Profesor {
    private String cedula;
    private String nombre;
    private String sexo;
    private String email;

    public Profesor() {
    }

    public Profesor(String cedula, String nombre, String sexo, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.sexo = sexo;
        this.email = email;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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
}

