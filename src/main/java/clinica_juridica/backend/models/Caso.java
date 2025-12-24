package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Objects;

@Table("casos")
public class Caso implements Persistable<String> {

    @Id
    private String numCaso;
    private LocalDate fechaRecepcion;
    private String sintesis;
    private String tramite;
    private Integer cantBeneficiarios;
    private String estatus;
    private String codCasoTribunal;
    private LocalDate fechaResCasoTri;
    private LocalDate fechaCreaCasoTri;
    private Integer idTribunal;
    private String termino;
    private Integer idCentro;
    private String cedula;
    private String username;
    private Integer comAmbLegal;

    public Caso() {
    }

    public Caso(String numCaso, LocalDate fechaRecepcion, String sintesis, String tramite, Integer cantBeneficiarios,
            String estatus, String codCasoTribunal, LocalDate fechaResCasoTri, LocalDate fechaCreaCasoTri,
            Integer idTribunal, String termino, Integer idCentro, String cedula, String username, Integer comAmbLegal) {
        this.numCaso = numCaso;
        this.fechaRecepcion = fechaRecepcion;
        this.sintesis = sintesis;
        this.tramite = tramite;
        this.cantBeneficiarios = cantBeneficiarios;
        this.estatus = estatus;
        this.codCasoTribunal = codCasoTribunal;
        this.fechaResCasoTri = fechaResCasoTri;
        this.fechaCreaCasoTri = fechaCreaCasoTri;
        this.idTribunal = idTribunal;
        this.termino = termino;
        this.idCentro = idCentro;
        this.cedula = cedula;
        this.username = username;
        this.comAmbLegal = comAmbLegal;
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

    public String getSintesis() {
        return sintesis;
    }

    public void setSintesis(String sintesis) {
        this.sintesis = sintesis;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
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

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getComAmbLegal() {
        return comAmbLegal;
    }

    public void setComAmbLegal(Integer comAmbLegal) {
        this.comAmbLegal = comAmbLegal;
    }

    @Override
    public String getId() {
        return numCaso;
    }

    @Override
    public boolean isNew() {
        return true; // Simplified
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Caso caso = (Caso) o;
        return Objects.equals(numCaso, caso.numCaso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numCaso);
    }

    @Override
    public String toString() {
        return "Caso{" +
                "numCaso='" + numCaso + '\'' +
                ", estatus='" + estatus + '\'' +
                ", tramite='" + tramite + '\'' +
                '}';
    }
}
