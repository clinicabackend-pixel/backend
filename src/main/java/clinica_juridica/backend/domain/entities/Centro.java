package clinica_juridica.backend.domain.entities;

public class Centro {
    private Integer idCentro;
    private String nombre;
    private Integer idParroquia;

    public Centro() {
    }

    public Centro(Integer idCentro, String nombre, Integer idParroquia) {
        this.idCentro = idCentro;
        this.nombre = nombre;
        this.idParroquia = idParroquia;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(Integer idParroquia) {
        this.idParroquia = idParroquia;
    }
}

