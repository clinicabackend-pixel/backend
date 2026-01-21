package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información básica de un profesor")
public class ProfesorResponse {
    @Schema(description = "Nombre de usuario del profesor")
    private String username;
    @Schema(description = "Nombre completo del profesor")
    private String nombre;
    @Schema(description = "Cédula del profesor")
    private String cedula;
    @Schema(description = "Término académico asociado")
    private String termino;
    @Schema(description = "Correo electrónico")
    private String email;

    public ProfesorResponse(String username, String nombre, String cedula, String termino, String email) {
        this.username = username;
        this.nombre = nombre;
        this.cedula = cedula;
        this.termino = termino;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
