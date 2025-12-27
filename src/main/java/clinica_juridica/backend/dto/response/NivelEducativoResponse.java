package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información de nivel educativo")
public class NivelEducativoResponse {
    @Schema(description = "ID del nivel educativo")
    private Integer id;
    @Schema(description = "Nombre del nivel")
    private String nivel;

    public NivelEducativoResponse(Integer id, String nivel) {
        this.id = id;
        this.nivel = nivel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
