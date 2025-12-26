package clinica_juridica.backend.dto.response;

import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.dto.projection.CasoAsignadoProjection;
import clinica_juridica.backend.dto.projection.CasoSupervisadoProjection;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Detalle completo de un caso con todas sus relaciones")
public class CasoDetalleResponse {

    @Schema(description = "Información principal del caso")
    private Caso caso;

    @Schema(description = "Lista de acciones realizadas")
    private List<AccionResponse> acciones;

    @Schema(description = "Lista de encuentros programados")
    private List<EncuentroResponse> encuentros;

    @Schema(description = "Lista de documentos asociados")
    private List<DocumentoResponse> documentos;

    @Schema(description = "Lista de pruebas registradas")
    private List<PruebaResponse> pruebas;

    @Schema(description = "Lista de estudiantes/abogados asignados")
    private List<CasoAsignadoProjection> asignados;

    @Schema(description = "Lista de supervisores asignados")
    private List<CasoSupervisadoProjection> supervisores;

    public CasoDetalleResponse(Caso caso, List<AccionResponse> acciones, List<EncuentroResponse> encuentros,
            List<DocumentoResponse> documentos, List<PruebaResponse> pruebas,
            List<CasoAsignadoProjection> asignados, List<CasoSupervisadoProjection> supervisores) {
        this.caso = caso;
        this.acciones = acciones;
        this.encuentros = encuentros;
        this.documentos = documentos;
        this.pruebas = pruebas;
        this.asignados = asignados;
        this.supervisores = supervisores;
    }

    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    public List<AccionResponse> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<AccionResponse> acciones) {
        this.acciones = acciones;
    }

    public List<EncuentroResponse> getEncuentros() {
        return encuentros;
    }

    public void setEncuentros(List<EncuentroResponse> encuentros) {
        this.encuentros = encuentros;
    }

    public List<DocumentoResponse> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoResponse> documentos) {
        this.documentos = documentos;
    }

    public List<PruebaResponse> getPruebas() {
        return pruebas;
    }

    public void setPruebas(List<PruebaResponse> pruebas) {
        this.pruebas = pruebas;
    }

    public List<CasoAsignadoProjection> getAsignados() {
        return asignados;
    }

    public void setAsignados(List<CasoAsignadoProjection> asignados) {
        this.asignados = asignados;
    }

    public List<CasoSupervisadoProjection> getSupervisores() {
        return supervisores;
    }

    public void setSupervisores(List<CasoSupervisadoProjection> supervisores) {
        this.supervisores = supervisores;
    }
}
