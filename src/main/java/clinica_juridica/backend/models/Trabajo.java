package clinica_juridica.backend.domain.models;

public class Trabajo {
    private String idTrabajo;
    private String trabaja;
    private String condicionActividad;
    private String condicionTrabajo;
    private String estaBuscando;

    public Trabajo() {
    }

    public Trabajo(String idTrabajo, String trabaja, String condicionActividad,
            String condicionTrabajo, String estaBuscando) {
        this.idTrabajo = idTrabajo;
        this.trabaja = trabaja;
        this.condicionActividad = condicionActividad;
        this.condicionTrabajo = condicionTrabajo;
        this.estaBuscando = estaBuscando;
    }

    public String getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(String idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public String getTrabaja() {
        return trabaja;
    }

    public void setTrabaja(String trabaja) {
        this.trabaja = trabaja;
    }

    public String getCondicionActividad() {
        return condicionActividad;
    }

    public void setCondicionActividad(String condicionActividad) {
        this.condicionActividad = condicionActividad;
    }

    public String getCondicionTrabajo() {
        return condicionTrabajo;
    }

    public void setCondicionTrabajo(String condicionTrabajo) {
        this.condicionTrabajo = condicionTrabajo;
    }

    public String getEstaBuscando() {
        return estaBuscando;
    }

    public void setEstaBuscando(String estaBuscando) {
        this.estaBuscando = estaBuscando;
    }
}
