package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de creación o actualización de usuario")
public class UsuarioRequest {

        @Schema(description = "Cédula de identidad", example = "V-12345678")
        private String cedula;

        @Schema(description = "Nombre completo", example = "Juan Perez")
        private String nombre;

        @Schema(description = "Sexo", example = "Masculino")
        private String sexo;

        @Schema(description = "Correo electrónico", example = "juan@example.com")
        private String email;

        @Schema(description = "Nombre de usuario", example = "juanp")
        private String username;

        @Schema(description = "Contraseña (opcional en edición)", example = "123456")
        private String contrasena;

        @Schema(description = "Estatus (ACTIVO/INACTIVO)", example = "ACTIVO")
        private String estatus;

        @Schema(description = "Tipo de usuario (COORDINADOR, PROFESOR, ESTUDIANTE)", example = "ESTUDIANTE")
        private String tipoUsuario;

        // Campos específicos
        @Schema(description = "Término académico (Solo profesores/estudiantes)", example = "2024-15")
        private String termino;

        @Schema(description = "NRC (Solo estudiantes)", example = "12345")
        private Integer nrc;

        @Schema(description = "Tipo de estudiante (PREGRADO/POSTGRADO) (Solo estudiantes)", example = "PREGRADO")
        private String tipoDeEstudiante;

        public UsuarioRequest() {
        }

        // Getters and Setters
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

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getContrasena() {
                return contrasena;
        }

        public void setContrasena(String contrasena) {
                this.contrasena = contrasena;
        }

        public String getEstatus() {
                return estatus;
        }

        public void setEstatus(String estatus) {
                this.estatus = estatus;
        }

        public String getTipoUsuario() {
                return tipoUsuario;
        }

        public void setTipoUsuario(String tipoUsuario) {
                this.tipoUsuario = tipoUsuario;
        }

        public String getTermino() {
                return termino;
        }

        public void setTermino(String termino) {
                this.termino = termino;
        }

        public Integer getNrc() {
                return nrc;
        }

        public void setNrc(Integer nrc) {
                this.nrc = nrc;
        }

        public String getTipoDeEstudiante() {
                return tipoDeEstudiante;
        }

        public void setTipoDeEstudiante(String tipoDeEstudiante) {
                this.tipoDeEstudiante = tipoDeEstudiante;
        }
}
