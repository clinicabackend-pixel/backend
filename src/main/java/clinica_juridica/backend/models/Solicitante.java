package clinica_juridica.backend.models;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("usuarios")
public class Solicitante {
    @Id
    @Column("id_usuario")
    private String idSolicitante;

    @Column("nombre")
    private String nombre;

    @Column("sexo")
    private String sexo;

    @Column("email")
    private String email;

    @Column("edad")
    private Integer edad;

    @Column("nacionalidad")
    private String nacionalidad;

    @Column("f_nacimiento")
    private LocalDate fNacimiento;

    @Column("concubinato")
    private String concubinato;

    @Column("estado_civil")
    private String estadoCivil;

    @Column("descripcion_trabajo")
    private String descripcionTrabajo;

    @Column("id_nivel_edu")
    private Integer idNivelEdu;

    @Column("id_vivienda")
    private String idVivienda;

    @Column("id_trabajo")
    private String idTrabajo;

    @Column("id_parroquia")
    private Integer idParroquia;

    // idFamilia removed from schema but exists in POJO. It is FK in Familias table
    // pointing to Usuarios.
    // So Solicitante does not have id_familia column.
    // We should probably mark it as transient OR remove it if it's not in the
    // table.
    // The schema says: "-- Nota: Se eliminó id_familia porque la tabla Familias se
    // vincula a esta mediante id_solicitante"
    // So I will make it Transient or ignore it for now, but to avoid errors, I'll
    // add @Column(value = "id_familia") which will fail if column missing.
    // Better to remove it or mark @Transient. I will mark
    // @org.springframework.data.annotation.Transient

    @org.springframework.data.annotation.Transient
    private Integer idFamilia;

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
