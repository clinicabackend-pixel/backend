package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información básica de un estudiante")
public class EstudianteResponse {
    @Schema(description = "Nombre de usuario del estudiante")
    private String username;
    @Schema(description = "Nombre completo del estudiante")
    private String nombre;

    @Schema(description = "Cédula del estudiante")
    private String cedula;
    @Schema(description = "Término académico")
    private String termino;
    @Schema(description = "Tipo de estudiante (Regular/Tesista)")
    private String tipoDeEstudiante;
    @Schema(description = "NRC")
    private Integer nrc;

    public EstudianteResponse(String username, String nombre, String cedula, String termino, String tipoDeEstudiante,
            Integer nrc) {
        this.username = username;
        this.nombre = nombre;
        this.cedula = cedula;
        this.termino = termino;
        this.tipoDeEstudiante = tipoDeEstudiante;
        this.nrc = nrc;
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

    public String getTipoDeEstudiante() {
        return tipoDeEstudiante;
    }

    public void setTipoDeEstudiante(String tipoDeEstudiante) {
        this.tipoDeEstudiante = tipoDeEstudiante;
    }

    public Integer getNrc() {
        return nrc;
    }

    public void setNrc(Integer nrc) {
        this.nrc = nrc;
    }
}
