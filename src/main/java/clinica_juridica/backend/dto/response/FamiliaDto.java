package clinica_juridica.backend.dto.response;

import java.math.BigDecimal;

public record FamiliaDto(
        String cedula,
        Integer cantPersonas,
        BigDecimal ingresoMes,
        Boolean jefeFamilia,
        Integer cantNinos,
        Integer cantTrabaja) {
}
