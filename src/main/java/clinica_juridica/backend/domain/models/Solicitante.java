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
    private Integer idNivelEdu;
    private String idVivienda;
    private String idTrabajo;
    private Integer idFamilia;
    private Integer idParroquia;

    public Solicitante() {
    }

    public Solicitante(String idSolicitante, String nombre, String sexo, String email,
            Integer edad, String nacionalidad, LocalDate fNacimiento,
            String concubinato, String estadoCivil, String descripcionTrabajo,
            Integer idNivelEdu, String idVivienda, String idTrabajo,
            Integer idFamilia, Integer idParroquia) {
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
        this.idNivelEdu = idNivelEdu;
        this.idVivienda = idVivienda;
        this.idTrabajo = idTrabajo;
        this.idFamilia = idFamilia;
        this.idParroquia = idParroquia;
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

    public LocalDate getFNacimiento() {
        return fNacimiento;
    }

    public void setFNacimiento(LocalDate fNacimiento) {
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

    public Integer getIdNivelEdu() {
        return idNivelEdu;
    }

    public void setIdNivelEdu(Integer idNivelEdu) {
        this.idNivelEdu = idNivelEdu;
    }

    public String getIdVivienda() {
        return idVivienda;
    }

    public void setIdVivienda(String idVivienda) {
        this.idVivienda = idVivienda;
    }

    public String getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(String idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public Integer getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(Integer idFamilia) {
        this.idFamilia = idFamilia;
    }

    public Integer getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(Integer idParroquia) {
        this.idParroquia = idParroquia;
    }
}
