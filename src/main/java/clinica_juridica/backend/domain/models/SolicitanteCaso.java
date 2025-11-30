package clinica_juridica.backend.domain.models;

public class SolicitanteCaso {
    private String numCaso;
    private String idSolicitante;

    public SolicitanteCaso() {
    }

    public SolicitanteCaso(String numCaso, String idSolicitante) {
        this.numCaso = numCaso;
        this.idSolicitante = idSolicitante;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public String getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
    }
}

