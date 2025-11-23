package clinica_juridica.backend.domain.entities;

public class Tribunal {
    private Integer idTribunal;
    private String tipoTribunal;
    private String nombreTribunal;

    public Tribunal() {
    }

    public Tribunal(Integer idTribunal, String tipoTribunal, String nombreTribunal) {
        this.idTribunal = idTribunal;
        this.tipoTribunal = tipoTribunal;
        this.nombreTribunal = nombreTribunal;
    }

    public Integer getIdTribunal() {
        return idTribunal;
    }

    public void setIdTribunal(Integer idTribunal) {
        this.idTribunal = idTribunal;
    }

    public String getTipoTribunal() {
        return tipoTribunal;
    }

    public void setTipoTribunal(String tipoTribunal) {
        this.tipoTribunal = tipoTribunal;
    }

    public String getNombreTribunal() {
        return nombreTribunal;
    }

    public void setNombreTribunal(String nombreTribunal) {
        this.nombreTribunal = nombreTribunal;
    }
}

