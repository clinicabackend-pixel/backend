package clinica_juridica.backend.dto.request;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para actualizar un caso existente")
public class CasoUpdateRequest {
    @Schema(description = "Nueva síntesis del caso")
    private String sintesis;
    @Schema(description = "Código del caso en tribunal")
    private String codCasoTribunal;
    @Schema(description = "Fecha de resolución en tribunal")
    private LocalDate fechaResCasoTri;
    @Schema(description = "Fecha de creación en tribunal")
    private LocalDate fechaCreaCasoTri;
    @Schema(description = "ID del tribunal asociado")
    private Integer idTribunal;
    @Schema(description = "Competencia de ámbito legal")
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
