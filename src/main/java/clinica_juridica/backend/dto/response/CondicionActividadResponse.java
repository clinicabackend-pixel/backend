package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con información de situación/condición de actividad")
public class CondicionActividadResponse {
    @Schema(description = "ID de la condición de actividad")
    private Integer id;
    @Schema(description = "Nombre de la actividad")
    private String nombre;
    @Schema(description = "Estatus de la actividad")
    private String estatus;

    public CondicionActividadResponse(Integer id, String nombre, String estatus) {
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
