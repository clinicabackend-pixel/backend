package clinica_juridica.backend.models;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Telefonos")
public class Telefono {
    @Column("id_solicitante")
    private String idSolicitante;

    @Column("telefono")
    private String telefono;

    public Telefono() {
    }

    public Telefono(String idSolicitante, String telefono) {
        this.idSolicitante = idSolicitante;
        this.telefono = telefono;
    }

    public String getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
