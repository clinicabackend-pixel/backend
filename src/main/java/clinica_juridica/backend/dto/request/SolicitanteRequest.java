package clinica_juridica.backend.dto.request;

import java.time.LocalDate;

public record SolicitanteRequest(
        String idSolicitante,
        String nombre,
        String sexo,
        LocalDate fechaNacimiento,
        String estadoCivil,
        Integer numHijos,
        String direccion,
        Integer idParroquia,
        Integer idNivelEducativo,
        String idVivienda,
        String idTrabajo,
        Integer idFamilia, // Transient handled manually in Service usually, but here just passing ID
        Integer ingresoFamiliar) {
}
