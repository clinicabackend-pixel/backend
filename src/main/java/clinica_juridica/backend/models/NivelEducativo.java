package clinica_juridica.backend.domain.models;

public class NivelEducativo {
    private Integer idNivelEdu;
    private String nivel;
    private Integer anio;

    public NivelEducativo() {
    }

    public NivelEducativo(Integer idNivelEdu, String nivel, Integer anio) {
        this.idNivelEdu = idNivelEdu;
        this.nivel = nivel;
        this.anio = anio;
    }

    public Integer getIdNivelEdu() {
        return idNivelEdu;
    }

    public void setIdNivelEdu(Integer idNivelEdu) {
        this.idNivelEdu = idNivelEdu;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
}
