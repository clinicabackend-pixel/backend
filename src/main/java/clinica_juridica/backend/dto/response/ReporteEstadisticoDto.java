package clinica_juridica.backend.dto.response;

import java.util.List;

public class ReporteEstadisticoDto {

    private List<CasoPorMateriaDto> casosPorMateria;
    private List<CasoPorParroquiaDto> casosPorParroquia;
    private BeneficiariosDto beneficiarios;
    private List<HistoricoCasosDto> historicoCasos;

    public ReporteEstadisticoDto() {
    }

    public ReporteEstadisticoDto(List<CasoPorMateriaDto> casosPorMateria, List<CasoPorParroquiaDto> casosPorParroquia,
            BeneficiariosDto beneficiarios, List<HistoricoCasosDto> historicoCasos) {
        this.casosPorMateria = casosPorMateria;
        this.casosPorParroquia = casosPorParroquia;
        this.beneficiarios = beneficiarios;
        this.historicoCasos = historicoCasos;
    }

    public List<CasoPorMateriaDto> getCasosPorMateria() {
        return casosPorMateria;
    }

    public void setCasosPorMateria(List<CasoPorMateriaDto> casosPorMateria) {
        this.casosPorMateria = casosPorMateria;
    }

    public List<CasoPorParroquiaDto> getCasosPorParroquia() {
        return casosPorParroquia;
    }

    public void setCasosPorParroquia(List<CasoPorParroquiaDto> casosPorParroquia) {
        this.casosPorParroquia = casosPorParroquia;
    }

    public BeneficiariosDto getBeneficiarios() {
        return beneficiarios;
    }

    public void setBeneficiarios(BeneficiariosDto beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    public List<HistoricoCasosDto> getHistoricoCasos() {
        return historicoCasos;
    }

    public void setHistoricoCasos(List<HistoricoCasosDto> historicoCasos) {
        this.historicoCasos = historicoCasos;
    }

    public static class CasoPorMateriaDto {
        private String materia;
        private Long cantidad;

        public CasoPorMateriaDto() {
        }

        public CasoPorMateriaDto(String materia, Long cantidad) {
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

    public static class CasoPorParroquiaDto {
        private String parroquia;
        private Long cantidad;

        public CasoPorParroquiaDto() {
        }

        public CasoPorParroquiaDto(String parroquia, Long cantidad) {
            this.parroquia = parroquia;
            this.cantidad = cantidad;
        }

        public String getParroquia() {
            return parroquia;
        }

        public void setParroquia(String parroquia) {
            this.parroquia = parroquia;
        }

        public Long getCantidad() {
            return cantidad;
        }

        public void setCantidad(Long cantidad) {
            this.cantidad = cantidad;
        }
    }

    public static class BeneficiariosDto {
        private Long directos;
        private Long indirectos;

        public BeneficiariosDto() {
        }

        public BeneficiariosDto(Long directos, Long indirectos) {
            this.directos = directos;
            this.indirectos = indirectos;
        }

        public Long getDirectos() {
            return directos;
        }

        public void setDirectos(Long directos) {
            this.directos = directos;
        }

        public Long getIndirectos() {
            return indirectos;
        }

        public void setIndirectos(Long indirectos) {
            this.indirectos = indirectos;
        }
    }

    public static class HistoricoCasosDto {
        private Integer anio;
        private Long cantidad;

        public HistoricoCasosDto() {
        }

        public HistoricoCasosDto(Integer anio, Long cantidad) {
            this.anio = anio;
            this.cantidad = cantidad;
        }

        public Integer getAnio() {
            return anio;
        }

        public void setAnio(Integer anio) {
            this.anio = anio;
        }

        public Long getCantidad() {
            return cantidad;
        }

        public void setCantidad(Long cantidad) {
            this.cantidad = cantidad;
        }
    }
}
