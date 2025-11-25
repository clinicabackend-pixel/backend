package clinica_juridica.backend.application.port.output;

import clinica_juridica.backend.domain.entities.Caso;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (interfaz de repositorio) para la entidad Caso.
 * Define las operaciones de persistencia necesarias para los casos de uso.
 */
public interface CasoRepository {
    
    /**
     * Guarda un nuevo caso en la base de datos.
     * @param caso El caso a guardar
     * @return El caso guardado con su ID generado
     */
    Caso guardar(Caso caso);
    
    /**
     * Busca un caso por su número de caso.
     * @param numCaso El número de caso
     * @return Un Optional con el caso si existe
     */
    Optional<Caso> buscarPorNumCaso(String numCaso);
    
    /**
     * Busca todos los casos asociados a un solicitante.
     * @param idSolicitante El ID del solicitante
     * @return Lista de casos del solicitante
     */
    List<Caso> buscarPorSolicitante(String idSolicitante);
    
    /**
     * Busca todos los casos.
     * @return Lista de todos los casos
     */
    List<Caso> buscarTodos();
}

