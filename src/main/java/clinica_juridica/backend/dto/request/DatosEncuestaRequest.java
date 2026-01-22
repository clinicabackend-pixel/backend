package clinica_juridica.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Objeto agrupar para guardar datos socioeconómicos de la encuesta")
public class DatosEncuestaRequest {

    @Schema(description = "Datos del grupo familiar")
    private FamiliaDTO familia;

    @Schema(description = "Datos básicos de la vivienda")
    private ViviendaDTO vivienda;

    @Schema(description = "Lista de características seleccionadas (checklist)")
    private List<CaracteristicaRequest> caracteristicas;

    @Schema(description = "ID Condición laboral")
    private Integer idCondicionLaboral;

    @Schema(description = "ID Condición de actividad")
    private Integer idCondicionActividad;

    public FamiliaDTO getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaDTO familia) {
        this.familia = familia;
    }

    public ViviendaDTO getVivienda() {
        return vivienda;
    }

    public void setVivienda(ViviendaDTO vivienda) {
        this.vivienda = vivienda;
    }

    public List<CaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<CaracteristicaRequest> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Integer getIdCondicionLaboral() {
        return idCondicionLaboral;
    }

    public void setIdCondicionLaboral(Integer idCondicionLaboral) {
        this.idCondicionLaboral = idCondicionLaboral;
    }

    public Integer getIdCondicionActividad() {
        return idCondicionActividad;
    }

    public void setIdCondicionActividad(Integer idCondicionActividad) {
        this.idCondicionActividad = idCondicionActividad;
    }

    // Inner DTOs for organization
    public static class FamiliaDTO {
        private Integer cantPersonas;
        private Integer cantEstudiando;
        private BigDecimal ingresoMes;
        private Boolean jefeFamilia;
        private Integer cantSinTrabajo;
        private Integer cantNinos;
        private Integer cantTrabaja;
        private Integer idNivelEduJefe;
        private String tiempoEstudio;

        // Getters and Setters
        public Integer getCantPersonas() {
            return cantPersonas;
        }

        public void setCantPersonas(Integer cantPersonas) {
            this.cantPersonas = cantPersonas;
        }

        public Integer getCantEstudiando() {
            return cantEstudiando;
        }

        public void setCantEstudiando(Integer cantEstudiando) {
            this.cantEstudiando = cantEstudiando;
        }

        public BigDecimal getIngresoMes() {
            return ingresoMes;
        }

        public void setIngresoMes(BigDecimal ingresoMes) {
            this.ingresoMes = ingresoMes;
        }

        public Boolean getJefeFamilia() {
            return jefeFamilia;
        }

        public void setJefeFamilia(Boolean jefeFamilia) {
            this.jefeFamilia = jefeFamilia;
        }

        public Integer getCantSinTrabajo() {
            return cantSinTrabajo;
        }

        public void setCantSinTrabajo(Integer cantSinTrabajo) {
            this.cantSinTrabajo = cantSinTrabajo;
        }

        public Integer getCantNinos() {
            return cantNinos;
        }

        public void setCantNinos(Integer cantNinos) {
            this.cantNinos = cantNinos;
        }

        public Integer getCantTrabaja() {
            return cantTrabaja;
        }

        public void setCantTrabaja(Integer cantTrabaja) {
            this.cantTrabaja = cantTrabaja;
        }

        public Integer getIdNivelEduJefe() {
            return idNivelEduJefe;
        }

        public void setIdNivelEduJefe(Integer idNivelEduJefe) {
            this.idNivelEduJefe = idNivelEduJefe;
        }

        public String getTiempoEstudio() {
            return tiempoEstudio;
        }

        public void setTiempoEstudio(String tiempoEstudio) {
            this.tiempoEstudio = tiempoEstudio;
        }
    }

    public static class ViviendaDTO {
        private Integer cantHabitaciones;
        private Integer cantBanos;

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
    }
}
