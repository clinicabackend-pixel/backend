package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Objects;

@Table("solicitantes")
public class Solicitante implements Persistable<String> {

    @Id
    private String cedula;
    private String nombre;
    private String nacionalidad;
    private String sexo;
    private String email;
    private String concubinato;
    private Integer idEstadoCivil;
    private Integer idParroquia;
    private String telfCelular;
    private String telfCasa;
    private LocalDate fNacimiento;
    private Integer edad;
    private Integer idCondicion;
    private Integer idCondicionActividad;
    private Integer idNivel;
    private String tiempoEstudio;

    public Solicitante() {
    }

    public Solicitante(String cedula, String nombre, String nacionalidad, String sexo, String email, String concubinato,
            Integer idEstadoCivil, String telfCelular, String telfCasa, LocalDate fNacimiento, Integer edad,
            Integer idCondicion, Integer idCondicionActividad, Integer idNivel, String tiempoEstudio) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.sexo = sexo;
        this.email = email;
        this.concubinato = concubinato;
        this.idEstadoCivil = idEstadoCivil;
        this.telfCelular = telfCelular;
        this.telfCasa = telfCasa;
        this.fNacimiento = fNacimiento;
        this.edad = edad;
        this.idCondicion = idCondicion;
        this.idCondicionActividad = idCondicionActividad;
        this.idNivel = idNivel;
        this.tiempoEstudio = tiempoEstudio;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
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

    public String getConcubinato() {
        return concubinato;
    }

    public void setConcubinato(String concubinato) {
        this.concubinato = concubinato;
    }

    public Integer getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(Integer idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public Integer getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(Integer idParroquia) {
        this.idParroquia = idParroquia;
    }

    public String getTelfCelular() {
        return telfCelular;
    }

    public void setTelfCelular(String telfCelular) {
        this.telfCelular = telfCelular;
    }

    public String getTelfCasa() {
        return telfCasa;
    }

    public void setTelfCasa(String telfCasa) {
        this.telfCasa = telfCasa;
    }

    public LocalDate getFNacimiento() {
        return fNacimiento;
    }

    public void setFNacimiento(LocalDate fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getIdCondicion() {
        return idCondicion;
    }

    public void setIdCondicion(Integer idCondicion) {
        this.idCondicion = idCondicion;
    }

    public Integer getIdCondicionActividad() {
        return idCondicionActividad;
    }

    public void setIdCondicionActividad(Integer idCondicionActividad) {
        this.idCondicionActividad = idCondicionActividad;
    }

    public Integer getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(Integer idNivel) {
        this.idNivel = idNivel;
    }

    public String getTiempoEstudio() {
        return tiempoEstudio;
    }

    public void setTiempoEstudio(String tiempoEstudio) {
        this.tiempoEstudio = tiempoEstudio;
    }

    @org.springframework.data.annotation.Transient
    private boolean isNew = true;

    @Override
    public String getId() {
        return cedula;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Solicitante that = (Solicitante) o;
        return Objects.equals(cedula, that.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    @Override
    public String toString() {
        return "Solicitante{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
