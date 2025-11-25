package clinica_juridica.backend.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Configuración de la base de datos PostgreSQL.
 * Configura el DataSource, JdbcTemplate y gestión de transacciones.
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * Configura el DataSource usando HikariCP (pool de conexiones de alto rendimiento).
     * HikariCP es el pool por defecto en Spring Boot por su excelente performance.
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        
        // Configuración de conexión
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setUsername(dbUsername);
        hikariConfig.setPassword(dbPassword);
        hikariConfig.setDriverClassName(driverClassName);
        
        // Configuración del pool
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setConnectionTimeout(20000); // 20 segundos
        hikariConfig.setIdleTimeout(300000);       // 5 minutos
        hikariConfig.setMaxLifetime(1200000);      // 20 minutos
        
        // Configuraciones adicionales para PostgreSQL
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("ClinicaJuridicaHikariPool");
        
        // Propiedades específicas de PostgreSQL
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        return new HikariDataSource(hikariConfig);
    }

    /**
     * Configura el JdbcTemplate que será inyectado en los adaptadores de persistencia.
     * JdbcTemplate simplifica el uso de JDBC eliminando boilerplate code.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        // Configuraciones opcionales del JdbcTemplate
        jdbcTemplate.setQueryTimeout(30); // 30 segundos timeout para queries
        jdbcTemplate.setFetchSize(100);   // Número de filas a recuperar por vez
        
        return jdbcTemplate;
    }

    /**
     * Configura el gestor de transacciones.
     * Permite usar @Transactional en los casos de uso si es necesario.
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}

