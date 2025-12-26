package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Tipo de categoría de vivienda (agrupador)")
public record TipoViviendaResponse(
        @Schema(description = "ID del tipo", example = "1") Integer id,
        @Schema(description = "Nombre del tipo", example = "TIPO DE PISO") String nombre,
        @Schema(description = "Lista de categorías asociadas") List<CategoriaViviendaResponse> categorias) {
}
