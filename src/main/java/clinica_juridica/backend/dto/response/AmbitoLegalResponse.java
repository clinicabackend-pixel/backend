package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representación normalizada de un ámbito legal")
public class AmbitoLegalResponse {

    @Schema(description = "Identificador único del ámbito legal", example = "1")
    private Integer id;

    @Schema(description = "Descripción o nombre del ámbito legal", example = "CIVIL")
    private String descripcion;

    @Schema(description = "Tipo de nodo (MATERIA, CATEGORIA, SUBCATEGORIA)", example = "MATERIA")
    private String tipo;

    @Schema(description = "Nodos hijos (si aplica)")
    private java.util.List<AmbitoLegalResponse> children;

    public AmbitoLegalResponse() {
    }

    public AmbitoLegalResponse(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public AmbitoLegalResponse(Integer id, String descripcion, String tipo,
            java.util.List<AmbitoLegalResponse> children) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public java.util.List<AmbitoLegalResponse> getChildren() {
        return children;
    }

    public void setChildren(java.util.List<AmbitoLegalResponse> children) {
        this.children = children;
    }
}
