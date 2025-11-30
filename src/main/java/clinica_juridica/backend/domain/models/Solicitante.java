package clinica_juridica.backend.domain.models;

import java.time.LocalDate;

public class Solicitante {
    private String idSolicitante;
    private String nombre;
    private String sexo;
    private String email;
    private Integer edad;
    private String nacionalidad;
    private LocalDate fNacimiento;
    private String concubinato;
    private String estadoCivil;
    private String descripcionTrabajo;
    private String tipoTrabajo;
    private Integer idNivelEdu;

    public Solicitante() {
    }

    public Solicitante(String idSolicitante, String nombre, String sexo, String email, 
                       Integer edad, String nacionalidad, LocalDate fNacimiento, 
                       String concubinato, String estadoCivil, String descripcionTrabajo, 
                       String tipoTrabajo, Integer idNivelEdu) {
        this.idSolicitante = idSolicitante;
        this.nombre = nombre;
        this.sexo = sexo;
        this.email = email;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.fNacimiento = fNacimiento;
        this.concubinato = concubinato;
        this.estadoCivil = estadoCivil;
        this.descripcionTrabajo = descripcionTrabajo;
        this.tipoTrabajo = tipoTrabajo;
        this.idNivelEdu = idNivelEdu;
    }

    public String getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(LocalDate fNacimiento) {
        this.fNacimiento = fNacimiento;
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

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public Integer getIdNivelEdu() {
        return idNivelEdu;
    }

    public void setIdNivelEdu(Integer idNivelEdu) {
        this.idNivelEdu = idNivelEdu;
    }
}

