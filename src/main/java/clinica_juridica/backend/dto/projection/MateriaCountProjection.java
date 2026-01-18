package clinica_juridica.backend.dto.projection;

public class MateriaCountProjection {
    private String materia;
    private Long cantidad;

    public MateriaCountProjection(String materia, Long cantidad) {
        this.materia = materia;
        this.cantidad = cantidad;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
