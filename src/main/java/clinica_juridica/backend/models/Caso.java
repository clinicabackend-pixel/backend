package clinica_juridica.backend.models;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("casos")
public class Caso {
    @Id
    @Column("num_caso")
    private String numCaso;

    @Column("fecha_recepcion")
    private LocalDate fechaRecepcion;

    @Column("cant_beneficiarios")
    private Integer cantBeneficiarios;

    @Column("estatus")
    private String estatus;

    @Column("sintesis")
    private String sintesis;

    @Column("id_centro")
    private Integer idCentro;

    @Column("id_ambito_legal")
    private Integer idAmbitoLegal;

    @Column("id_solicitante")
    private String idSolicitante;

    public Caso() {
    }

    public Caso(String numCaso, LocalDate fechaRecepcion, Integer cantBeneficiarios,
            String tramite, String estatus, String sintesis, Integer idCentro,
            Integer idAmbitoLegal, String idSolicitante) {
        this.numCaso = numCaso;
        this.fechaRecepcion = fechaRecepcion;
        this.cantBeneficiarios = cantBeneficiarios;
        this.estatus = estatus;
        this.sintesis = sintesis;
        this.idCentro = idCentro;
        this.idAmbitoLegal = idAmbitoLegal;
        this.idSolicitante = idSolicitante;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Integer getCantBeneficiarios() {
        return cantBeneficiarios;
    }

    public void setCantBeneficiarios(Integer cantBeneficiarios) {
        this.cantBeneficiarios = cantBeneficiarios;
    }


    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getSintesis() {
        return sintesis;
    }

    public void setSintesis(String sintesis) {
        this.sintesis = sintesis;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public Integer getIdAmbitoLegal() {
        return idAmbitoLegal;
    }

    public void setIdAmbitoLegal(Integer idAmbitoLegal) {
        this.idAmbitoLegal = idAmbitoLegal;
    }

    public String getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
    }
}
