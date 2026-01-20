package clinica_juridica.backend.dto.response;

public record TribunalResponse(
                Integer idTribunal,
                String tipoTribunal,
                String nombreTribunal,
                String materia,
                String instancia,
                String ubicacion,
                String estatus) {
}
