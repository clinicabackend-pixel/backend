package clinica_juridica.backend.domain.models;

public class NivelEducativo {
    private Integer idNivelEdu;
    private String nivel;
    private Integer ano;

    public NivelEducativo() {
    }

    public NivelEducativo(Integer idNivelEdu, String nivel, Integer ano) {
        this.idNivelEdu = idNivelEdu;
        this.nivel = nivel;
        this.ano = ano;
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

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}

