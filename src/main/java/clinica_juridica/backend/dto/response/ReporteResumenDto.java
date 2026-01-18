package clinica_juridica.backend.dto.response;

public class ReporteResumenDto {
    private String estatus;
    private Long cantidad;

    public ReporteResumenDto(String estatus, Long cantidad) {
        this.estatus = estatus;
        this.cantidad = cantidad;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
