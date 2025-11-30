package clinica_juridica.backend.application.usecase;

import clinica_juridica.backend.application.port.output.CasoRepository;
import clinica_juridica.backend.domain.models.Caso;

import java.util.List;

/**
 * Caso de uso para obtener todos los casos asociados a un solicitante.
 */
public class ObtenerCasosPorSolicitante {
    
    private final CasoRepository casoRepository;
    
    public ObtenerCasosPorSolicitante(CasoRepository casoRepository) {
        this.casoRepository = casoRepository;
    }
    
    /**
     * Ejecuta la consulta de casos por solicitante.
     * @param idSolicitante El ID del solicitante
     * @return Lista de casos asociados al solicitante
     */
    public List<Caso> ejecutar(String idSolicitante) {
        if (idSolicitante == null || idSolicitante.isBlank()) {
            throw new IllegalArgumentException("El ID del solicitante no puede estar vacío");
        }
        
        return casoRepository.buscarPorSolicitante(idSolicitante);
    }
}

