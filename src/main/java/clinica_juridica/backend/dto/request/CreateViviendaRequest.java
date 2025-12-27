package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para crear una categoría de vivienda")
public class CreateViviendaRequest {
    @Schema(description = "ID del Tipo de Vivienda (Categoría Padre)", example = "1")
    private Integer idTipo;

    @Schema(description = "Descripción de la Vivienda (Característica)", example = "Casa Duplex")
    private String descripcion;

    public CreateViviendaRequest() {
    }

    public CreateViviendaRequest(Integer idTipo, String descripcion) {
        this.idTipo = idTipo;
        this.descripcion = descripcion;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
