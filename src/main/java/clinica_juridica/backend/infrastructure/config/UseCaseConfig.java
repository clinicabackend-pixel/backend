package clinica_juridica.backend.infrastructure.config;

import clinica_juridica.backend.application.port.output.CasoRepository;
import clinica_juridica.backend.application.usecase.ObtenerCasosPorSolicitante;
import clinica_juridica.backend.application.usecase.RegistrarCaso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Spring que define los beans de casos de uso.
 * Aquí se realiza la inyección de dependencias entre capas respetando Clean Architecture:
 * - Los casos de uso (application) dependen de interfaces/puertos
 * - Las implementaciones (infrastructure) se inyectan desde aquí
 */
@Configuration
public class UseCaseConfig {
    
    /**
     * Bean del caso de uso para registrar/crear casos.
     * Inyecta el repositorio (implementación desde infrastructure).
     * 
     * @param casoRepository Implementación del puerto de salida (CasoJdbcAdapter)
     * @return Instancia del caso de uso
     */
    @Bean
    public RegistrarCaso registrarCaso(CasoRepository casoRepository) {
        return new RegistrarCaso(casoRepository);
    }
    
    /**
     * Bean del caso de uso para obtener casos por solicitante.
     * 
     * @param casoRepository Implementación del puerto de salida (CasoJdbcAdapter)
     * @return Instancia del caso de uso
     */
    @Bean
    public ObtenerCasosPorSolicitante obtenerCasosPorSolicitante(CasoRepository casoRepository) {
        return new ObtenerCasosPorSolicitante(casoRepository);
    }
    
    // Aquí se pueden agregar más casos de uso relacionados con Caso en el futuro
}

