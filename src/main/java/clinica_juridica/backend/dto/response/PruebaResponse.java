package clinica_juridica.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Respuesta de una prueba registrada")
public class PruebaResponse {
    @Schema(description = "ID de la prueba")
    private Integer idPrueba;
    @Schema(description = "Número de caso")
    private String numCaso;
    @Schema(description = "Fecha")
    private LocalDate fecha;
    @Schema(description = "Título o documento de prueba")
    private String documento;
    @Schema(description = "Observación")
    private String observacion;
    @Schema(description = "Título")
    private String titulo;

    public PruebaResponse() {
    }

    public PruebaResponse(Integer idPrueba, String numCaso, LocalDate fecha, String documento, String observacion,
            String titulo) {
        this.idPrueba = idPrueba;
        this.numCaso = numCaso;
        this.fecha = fecha;
        this.documento = documento;
        this.observacion = observacion;
        this.titulo = titulo;
    }

    public Integer getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Integer idPrueba) {
        this.idPrueba = idPrueba;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
