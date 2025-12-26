package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categoría de vivienda")
public record CategoriaViviendaResponse(
        @Schema(description = "ID de la categoría", example = "1") Integer id,
        @Schema(description = "Descripción de la categoría", example = "CERAMICA") String descripcion,
        @Schema(description = "Estatus de la categoría", example = "ACTIVO") String estatus) {
}
