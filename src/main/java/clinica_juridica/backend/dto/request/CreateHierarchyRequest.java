package clinica_juridica.backend.dto.request;

public record CreateHierarchyRequest(
        String nombre,
        Integer idMateria,
        Integer idCategoria,
        Integer idSubcategoria) {
}
