package clinica_juridica.backend.models;

import java.time.LocalDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("pruebas")
public class Prueba {
    @Column("id_caso")
    private String idCaso;

    // id_prueba is SERIAL in schema, but part of composite PK.
    @Column("id_prueba")
    private Integer idPrueba;

    @Column("fecha")
    private LocalDate fecha;

    @Column("documento")
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
