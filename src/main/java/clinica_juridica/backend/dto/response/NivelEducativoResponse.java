package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información de nivel educativo")
public class NivelEducativoResponse {
    @Schema(description = "ID del nivel educativo")
    private Integer id;
    @Schema(description = "Nombre del nivel")
    private String nombre;
    @Schema(description = "Estatus del nivel")
    private String estatus;

    public NivelEducativoResponse(Integer id, String nombre, String estatus) {
        this.id = id;
        this.nombre = nombre;
        this.estatus = estatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
