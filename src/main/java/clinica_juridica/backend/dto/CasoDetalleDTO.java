package clinica_juridica.backend.dto;

import clinica_juridica.backend.models.*;
import java.util.List;

public class CasoDetalleDTO {
    private Caso caso;
    private List<Accion> acciones;
    private List<Encuentro> encuentros;
    private List<Documento> documentos;
    private List<Prueba> pruebas;

    public CasoDetalleDTO(Caso caso, List<Accion> acciones, List<Encuentro> encuentros, List<Documento> documentos,
            List<Prueba> pruebas) {
        this.caso = caso;
        this.acciones = acciones;
        this.encuentros = encuentros;
        this.documentos = documentos;
        this.pruebas = pruebas;
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
