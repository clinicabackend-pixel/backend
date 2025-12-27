package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información de condición laboral")
public class CondicionLaboralResponse {
    @Schema(description = "ID de la condición laboral")
    private Integer id;
    @Schema(description = "Descripción de la condición")
    private String condicion;

    public CondicionLaboralResponse(Integer id, String condicion) {
        this.id = id;
        this.condicion = condicion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}
