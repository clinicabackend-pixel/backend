package clinica_juridica.backend.domain.models;

public class Beneficiario {
    private String idBeneficiario;
    private String nombre;
    private String sexo;
    private String email;
    private Integer edad;
    private String parentesco;

    public Beneficiario() {
    }

    public Beneficiario(String idBeneficiario, String nombre, String sexo, 
                        String email, Integer edad, String parentesco) {
        this.idBeneficiario = idBeneficiario;
        this.nombre = nombre;
        this.sexo = sexo;
        this.email = email;
        this.edad = edad;
        this.parentesco = parentesco;
    }

    public String getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(String idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
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

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}

