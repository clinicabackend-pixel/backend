package clinica_juridica.backend.dto.request;

public record TribunalRequest(
        Integer idTribunal,
        String tipoTribunal,
        String nombreTribunal) {
}
