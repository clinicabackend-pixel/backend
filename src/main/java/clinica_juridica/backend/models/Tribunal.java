package clinica_juridica.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("tribunal")
public class Tribunal implements Persistable<Integer> {

    @Id
    private Integer idTribunal;
    private String nombreTribunal;
    private String materia;
    private String instancia;
    private String ubicacion;
    private String estatus;

    public Tribunal() {
    }

    public Tribunal(Integer idTribunal, String nombreTribunal, String materia, String instancia, String ubicacion,
            String estatus) {
        this.idTribunal = idTribunal;
        this.nombreTribunal = nombreTribunal;
        this.materia = materia;
        this.instancia = instancia;
        this.ubicacion = ubicacion;
        this.estatus = estatus;
    }

    public Integer getIdTribunal() {
        return idTribunal;
    }

    public void setIdTribunal(Integer idTribunal) {
        this.idTribunal = idTribunal;
    }

    public String getNombreTribunal() {
        return nombreTribunal;
    }

    public void setNombreTribunal(String nombreTribunal) {
        this.nombreTribunal = nombreTribunal;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getInstancia() {
        return instancia;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public Integer getId() {
        return idTribunal;
    }

    @Override
    public boolean isNew() {
        return idTribunal == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tribunal tribunal = (Tribunal) o;
        return Objects.equals(idTribunal, tribunal.idTribunal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTribunal);
    }

    @Override
    public String toString() {
        return "Tribunal{" +
                "idTribunal=" + idTribunal +
                ", nombreTribunal='" + nombreTribunal + '\'' +
                ", estatus='" + estatus + '\'' +
                '}';
    }
}
