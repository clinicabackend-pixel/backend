package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("profesor")
public class Profesor {
    @Id
    @Column("cedula")
    private String cedula;

    @Column("nombre")
    private String nombre;

    @Column("sexo")
    private String sexo;

    @Column("email")
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
