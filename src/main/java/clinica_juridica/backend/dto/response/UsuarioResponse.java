package clinica_juridica.backend.dto.response;

public record UsuarioResponse(String idUsuario, String nombre, String sexo, String email, String username,
                String estatus, String tipoUsuario, String termino) {
}
