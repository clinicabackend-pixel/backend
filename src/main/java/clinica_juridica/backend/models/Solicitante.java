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
    private String estadoCivil; // Changed from idEstadoCivil
    private String condicionLaboral; // Changed from idCondicion
    private String condicionActividad; // Changed from idCondicionActividad
    private String nivelEducativo; // Changed from idNivel

    // Estos campos siguen igual o necesitan revision si apuntan a catalogos
    private Integer idParroquia;
    private String telfCelular;
    private String telfCasa;
    private LocalDate fNacimiento;
    private Integer edad;
    private String tiempoEstudio;

    public Solicitante() {
    }

    public Solicitante(String cedula, String nombre, String nacionalidad, String sexo, String email, String concubinato,
            String estadoCivil, String telfCelular, String telfCasa, LocalDate fNacimiento, Integer edad,
            String condicionLaboral, String condicionActividad, String nivelEducativo, String tiempoEstudio) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.sexo = sexo;
        this.email = email;
        this.concubinato = concubinato;
        this.estadoCivil = estadoCivil;
        this.telfCelular = telfCelular;
        this.telfCasa = telfCasa;
        this.fNacimiento = fNacimiento;
        this.edad = edad;
        this.condicionLaboral = condicionLaboral;
        this.condicionActividad = condicionActividad;
        this.nivelEducativo = nivelEducativo;
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

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getCondicionLaboral() {
        return condicionLaboral;
    }

    public void setCondicionLaboral(String condicionLaboral) {
        this.condicionLaboral = condicionLaboral;
    }

    public String getCondicionActividad() {
        return condicionActividad;
    }

    public void setCondicionActividad(String condicionActividad) {
        this.condicionActividad = condicionActividad;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
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

    public String getTiempoEstudio() {
        return tiempoEstudio;
    }

    public void setTiempoEstudio(String tiempoEstudio) {
        this.tiempoEstudio = tiempoEstudio;
    }

    @org.springframework.data.annotation.Transient
    private boolean isNew = false;

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
