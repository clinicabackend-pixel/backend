package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de configuración de contraseña mediante token")
public class SetupPasswordRequest {

    @Schema(description = "Token de invitación recibido por correo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;

    @Schema(description = "Nueva contraseña", requiredMode = Schema.RequiredMode.REQUIRED, example = "nuevaClave123")
    private String contrasena;

    public SetupPasswordRequest() {
    }

    public SetupPasswordRequest(String token, String contrasena) {
        this.token = token;
        this.contrasena = contrasena;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
