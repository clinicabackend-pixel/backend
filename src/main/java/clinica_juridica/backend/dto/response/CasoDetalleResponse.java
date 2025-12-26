package clinica_juridica.backend.dto.response;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.dto.projection.CasoAsignadoProjection;
import clinica_juridica.backend.dto.projection.CasoSupervisadoProjection;
import java.util.List;

public class CasoDetalleResponse {
    private Caso caso;
    private List<Accion> acciones;
    private List<Encuentro> encuentros;
    private List<Documento> documentos;
    private List<Prueba> pruebas;
    private List<CasoAsignadoProjection> asignados;
    private List<CasoSupervisadoProjection> supervisores;

    public CasoDetalleResponse(Caso caso, List<Accion> acciones, List<Encuentro> encuentros, List<Documento> documentos,
            List<Prueba> pruebas, List<CasoAsignadoProjection> asignados,
            List<CasoSupervisadoProjection> supervisores) {
        this.caso = caso;
        this.acciones = acciones;
        this.encuentros = encuentros;
        this.documentos = documentos;
        this.pruebas = pruebas;
        this.asignados = asignados;
        this.supervisores = supervisores;
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

    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    public List<Accion> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<Accion> acciones) {
        this.acciones = acciones;
    }

    public List<Encuentro> getEncuentros() {
        return encuentros;
    }

    public void setEncuentros(List<Encuentro> encuentros) {
        this.encuentros = encuentros;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public List<Prueba> getPruebas() {
        return pruebas;
    }

    public void setPruebas(List<Prueba> pruebas) {
        this.pruebas = pruebas;
    }
}
