package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("tribunales")
public class Tribunal {
    @Id
    @Column("id_tribunal")
    private Integer idTribunal;

    @Column("tipo_tribunal")
    private String tipoTribunal;

    @Column("nombre_tribunal")
    private String nombreTribunal;

    public Tribunal() {
    }

    public Tribunal(Integer idTribunal, String tipoTribunal, String nombreTribunal) {
        this.idTribunal = idTribunal;
        this.tipoTribunal = tipoTribunal;
        this.nombreTribunal = nombreTribunal;
    }

    public Integer getIdTribunal() {
        return idTribunal;
    }

    public void setIdTribunal(Integer idTribunal) {
        this.idTribunal = idTribunal;
    }

    public String getTipoTribunal() {
        return tipoTribunal;
    }

    public void setTipoTribunal(String tipoTribunal) {
        this.tipoTribunal = tipoTribunal;
    }

    public String getNombreTribunal() {
        return nombreTribunal;
    }

    public void setNombreTribunal(String nombreTribunal) {
        this.nombreTribunal = nombreTribunal;
    }
}
