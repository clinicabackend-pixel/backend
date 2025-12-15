package clinica_juridica.backend.dto.response;

public record BeneficiarioResponse(
        String idBeneficiario,
        String nombre,
        String tipo,
        String parentesco) {
}
