package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Solicitud para actualizar la fecha de ejecución de una acción")
public class AccionExecutionDateRequest {

    @Schema(description = "Nueva fecha de ejecución", example = "2024-12-31")
    private LocalDate fechaEjecucion;

    @Schema(description = "Lista de usuarios que ejecutaron la acción")
    private java.util.List<String> usernames;

    public LocalDate getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDate fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public java.util.List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(java.util.List<String> usernames) {
        this.usernames = usernames;
    }
}
