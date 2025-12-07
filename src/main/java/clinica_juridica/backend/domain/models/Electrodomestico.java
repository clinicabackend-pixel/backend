package clinica_juridica.backend.domain.models;

public class Electrodomestico {
    private Integer idElectrodomestico;
    private String nombreElectrodomestico;

    public Electrodomestico() {
    }

    public Electrodomestico(Integer idElectrodomestico, String nombreElectrodomestico) {
        this.idElectrodomestico = idElectrodomestico;
        this.nombreElectrodomestico = nombreElectrodomestico;
    }

    public Integer getIdElectrodomestico() {
        return idElectrodomestico;
    }

    public void setIdElectrodomestico(Integer idElectrodomestico) {
        this.idElectrodomestico = idElectrodomestico;
    }

    public String getNombreElectrodomestico() {
        return nombreElectrodomestico;
    }

    public void setNombreElectrodomestico(String nombreElectrodomestico) {
        this.nombreElectrodomestico = nombreElectrodomestico;
    }
}
