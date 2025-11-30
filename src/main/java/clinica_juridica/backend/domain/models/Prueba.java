package clinica_juridica.backend.domain.models;

import java.time.LocalDate;

public class Prueba {
    private String idCaso;
    private Integer idPrueba;
    private LocalDate fecha;
    private String documento;

    public Prueba() {
    }

    public Prueba(String idCaso, Integer idPrueba, LocalDate fecha, String documento) {
        this.idCaso = idCaso;
        this.idPrueba = idPrueba;
        this.fecha = fecha;
        this.documento = documento;
    }

    public String getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(String idCaso) {
        this.idCaso = idCaso;
    }

    public Integer getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Integer idPrueba) {
        this.idPrueba = idPrueba;
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
}

