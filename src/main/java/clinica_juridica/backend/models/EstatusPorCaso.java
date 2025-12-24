package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("estatus_por_caso")
public class EstatusPorCaso {
    @Id
    private Integer idEstCaso;
    private String numCaso;
    private LocalDate fechaCambio;
    private String statusAnt;

    public EstatusPorCaso() {
    }

    public EstatusPorCaso(Integer idEstCaso, String numCaso, LocalDate fechaCambio, String statusAnt) {
        this.idEstCaso = idEstCaso;
        this.numCaso = numCaso;
        this.fechaCambio = fechaCambio;
        this.statusAnt = statusAnt;
    }

    public Integer getIdEstCaso() {
        return idEstCaso;
    }

    public void setIdEstCaso(Integer idEstCaso) {
        this.idEstCaso = idEstCaso;
    }

    public String getNumCaso() {
        return numCaso;
    }

    public void setNumCaso(String numCaso) {
        this.numCaso = numCaso;
    }

    public LocalDate getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDate fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getStatusAnt() {
        return statusAnt;
    }

    public void setStatusAnt(String statusAnt) {
        this.statusAnt = statusAnt;
    }

    @Override
    public String toString() {
        return "EstatusPorCaso{idEstCaso=" + idEstCaso + ", numCaso='" + numCaso + "', fechaCambio=" + fechaCambio
                + ", statusAnt='" + statusAnt + "'}";
    }
}
