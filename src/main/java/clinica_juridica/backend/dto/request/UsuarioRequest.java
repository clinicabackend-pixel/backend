package clinica_juridica.backend.dto.request;

public record UsuarioRequest(String idUsuario, String nombre, String sexo, String email, String username,
        String contrasena, String estatus, String tipoUsuario) {
}
