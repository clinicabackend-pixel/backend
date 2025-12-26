package clinica_juridica.backend.dto.request;

import java.time.LocalDate;

public class CasoUpdateRequest {
    private String sintesis;
    private String codCasoTribunal;
    private LocalDate fechaResCasoTri;
    private LocalDate fechaCreaCasoTri;
    private Integer idTribunal;
    private Integer comAmbLegal;

    public String getSintesis() {
        return sintesis;
    }

    public void setSintesis(String sintesis) {
        this.sintesis = sintesis;
    }

    public String getCodCasoTribunal() {
        return codCasoTribunal;
    }

    public void setCodCasoTribunal(String codCasoTribunal) {
        this.codCasoTribunal = codCasoTribunal;
    }

    public LocalDate getFechaResCasoTri() {
        return fechaResCasoTri;
    }

    public void setFechaResCasoTri(LocalDate fechaResCasoTri) {
        this.fechaResCasoTri = fechaResCasoTri;
    }

    public LocalDate getFechaCreaCasoTri() {
        return fechaCreaCasoTri;
    }

    public void setFechaCreaCasoTri(LocalDate fechaCreaCasoTri) {
        this.fechaCreaCasoTri = fechaCreaCasoTri;
    }

    public Integer getIdTribunal() {
        return idTribunal;
    }

    public void setIdTribunal(Integer idTribunal) {
        this.idTribunal = idTribunal;
    }

    public Integer getComAmbLegal() {
        return comAmbLegal;
    }

    public void setComAmbLegal(Integer comAmbLegal) {
        this.comAmbLegal = comAmbLegal;
    }
}
