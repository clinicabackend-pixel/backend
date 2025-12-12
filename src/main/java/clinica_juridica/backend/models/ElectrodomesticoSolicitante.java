package clinica_juridica.backend.models;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("electrodomesticos_solicitantes")
public class ElectrodomesticoSolicitante {

    @Column("id_solicitante")
    private String idSolicitante;

    @Column("id_electrodomestico")
    private Integer idElectrodomestico;

    public ElectrodomesticoSolicitante() {
    }

    public ElectrodomesticoSolicitante(String idSolicitante, Integer idElectrodomestico) {
        this.idSolicitante = idSolicitante;
        this.idElectrodomestico = idElectrodomestico;
    }

    public String getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Integer getIdElectrodomestico() {
        return idElectrodomestico;
    }

    public void setIdElectrodomestico(Integer idElectrodomestico) {
        this.idElectrodomestico = idElectrodomestico;
    }
}
