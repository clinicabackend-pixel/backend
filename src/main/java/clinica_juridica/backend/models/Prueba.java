package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("pruebas")
public class Prueba {
    @Id
    private Integer idPrueba;
    private String numCaso;
    private LocalDate fecha;
    private String documento;
    private String observacion;
    private String titulo;

    public Prueba() {
    }

    public Prueba(Integer idPrueba, String numCaso, LocalDate fecha, String documento, String observacion,
            String titulo) {
        this.idPrueba = idPrueba;
        this.numCaso = numCaso;
        this.fecha = fecha;
        this.documento = documento;
        this.observacion = observacion;
        this.titulo = titulo;
    }

    public Integer getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(Integer idPrueba) {
        this.idPrueba = idPrueba;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Prueba{idPrueba=" + idPrueba + ", titulo='" + titulo + "'}";
    }
}
