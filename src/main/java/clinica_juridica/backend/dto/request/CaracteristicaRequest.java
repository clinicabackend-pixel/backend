package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para asociar una característica de vivienda")
public class CaracteristicaRequest {

    @Schema(description = "ID del Tipo de Categoría (Ej: 1=Tipo Vivienda, 4=Techo)", example = "1")
    private Integer idTipoCat;

    @Schema(description = "ID de la Categoría seleccionada (Ej: 1=Quinta, 5=Rancho)", example = "1")
    private Integer idCatVivienda;

    public CaracteristicaRequest() {
    }

    public CaracteristicaRequest(Integer idTipoCat, Integer idCatVivienda) {
        this.idTipoCat = idTipoCat;
        this.idCatVivienda = idCatVivienda;
    }

    public Integer getIdTipoCat() {
        return idTipoCat;
    }

    public void setIdTipoCat(Integer idTipoCat) {
        this.idTipoCat = idTipoCat;
    }

    public Integer getIdCatVivienda() {
        return idCatVivienda;
    }

    public void setIdCatVivienda(Integer idCatVivienda) {
        this.idCatVivienda = idCatVivienda;
    }
}
