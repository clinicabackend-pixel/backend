package clinica_juridica.backend.domain.entities;

public class Vivienda {
    private String idSolicitante;
    private String tipo;
    private Integer cantHabitaciones;
    private Integer cantBanos;
    private String materialParedes;
    private String aguasNegras;
    private String servicioAgua;
    private String materialTecho;
    private String materialPiso;
    private String servicioAseo;

    public Vivienda() {
    }

    public Vivienda(String idSolicitante, String tipo, Integer cantHabitaciones, 
                    Integer cantBanos, String materialParedes, String aguasNegras, 
                    String servicioAgua, String materialTecho, String materialPiso, 
                    String servicioAseo) {
        this.idSolicitante = idSolicitante;
        this.tipo = tipo;
        this.cantHabitaciones = cantHabitaciones;
        this.cantBanos = cantBanos;
        this.materialParedes = materialParedes;
        this.aguasNegras = aguasNegras;
        this.servicioAgua = servicioAgua;
        this.materialTecho = materialTecho;
        this.materialPiso = materialPiso;
        this.servicioAseo = servicioAseo;
    }

    public String getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCantHabitaciones() {
        return cantHabitaciones;
    }

    public void setCantHabitaciones(Integer cantHabitaciones) {
        this.cantHabitaciones = cantHabitaciones;
    }

    public Integer getCantBanos() {
        return cantBanos;
    }

    public void setCantBanos(Integer cantBanos) {
        this.cantBanos = cantBanos;
    }

    public String getMaterialParedes() {
        return materialParedes;
    }

    public void setMaterialParedes(String materialParedes) {
        this.materialParedes = materialParedes;
    }

    public String getAguasNegras() {
        return aguasNegras;
    }

    public void setAguasNegras(String aguasNegras) {
        this.aguasNegras = aguasNegras;
    }

    public String getServicioAgua() {
        return servicioAgua;
    }

    public void setServicioAgua(String servicioAgua) {
        this.servicioAgua = servicioAgua;
    }

    public String getMaterialTecho() {
        return materialTecho;
    }

    public void setMaterialTecho(String materialTecho) {
        this.materialTecho = materialTecho;
    }

    public String getMaterialPiso() {
        return materialPiso;
    }

    public void setMaterialPiso(String materialPiso) {
        this.materialPiso = materialPiso;
    }

    public String getServicioAseo() {
        return servicioAseo;
    }

    public void setServicioAseo(String servicioAseo) {
        this.servicioAseo = servicioAseo;
    }
}

