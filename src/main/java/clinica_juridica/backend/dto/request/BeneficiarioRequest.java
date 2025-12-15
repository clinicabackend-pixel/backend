package clinica_juridica.backend.dto.request;

public record BeneficiarioRequest(
        String idBeneficiario,
        String tipo, // Directo/Indirecto
        String parentesco // Padre, Madre, Hijo, etc.
) {
}
