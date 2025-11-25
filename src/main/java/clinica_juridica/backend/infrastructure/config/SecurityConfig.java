package clinica_juridica.backend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de Spring Security.
 * NOTA: Esta configuración es PERMISIVA para desarrollo.
 * En producción se debe implementar autenticación y autorización adecuadas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura el SecurityFilterChain para permitir todas las peticiones.
     * Útil para desarrollo y pruebas de la API.
     * 
     * TODO: En producción, implementar:
     * - Autenticación JWT o Session-based
     * - Autorización basada en roles
     * - Protección CSRF apropiada
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF (útil para APIs REST sin estado)
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configurar autorización de requests
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso público a todos los endpoints (solo para desarrollo)
                .anyRequest().permitAll()
            );
        
        return http.build();
    }
}


