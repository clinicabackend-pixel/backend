package clinica_juridica.backend.dto.response;

import java.util.Map;

public class DashboardStatsDto {
    private long totalCasos;
    private long casosActivos;
    private long casosCerrados;
    private long totalSolicitantes;
    private Map<String, Long> distribucionMateria;
    private double porcentajeVulnerabilidad;

    public DashboardStatsDto(long totalCasos, long casosActivos, long casosCerrados, long totalSolicitantes,
            Map<String, Long> distribucionMateria, double porcentajeVulnerabilidad) {
        this.totalCasos = totalCasos;
        this.casosActivos = casosActivos;
        this.casosCerrados = casosCerrados;
        this.totalSolicitantes = totalSolicitantes;
        this.distribucionMateria = distribucionMateria;
        this.porcentajeVulnerabilidad = porcentajeVulnerabilidad;
    }

    // Getters
    public long getTotalCasos() {
        return totalCasos;
    }

    public long getCasosActivos() {
        return casosActivos;
    }

    public long getCasosCerrados() {
        return casosCerrados;
    }

    public long getTotalSolicitantes() {
        return totalSolicitantes;
    }

    public Map<String, Long> getDistribucionMateria() {
        return distribucionMateria;
    }

    public double getPorcentajeVulnerabilidad() {
        return porcentajeVulnerabilidad;
    }
}
