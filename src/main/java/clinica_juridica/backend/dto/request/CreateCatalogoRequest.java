package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para crear un elemento de catálogo simple")
public class CreateCatalogoRequest {
    @Schema(description = "Nombre o descripción del elemento", example = "Nuevo Elemento")
    private String nombre;

    public CreateCatalogoRequest() {
    }

    public CreateCatalogoRequest(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
