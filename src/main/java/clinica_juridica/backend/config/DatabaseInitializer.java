package clinica_juridica.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@Profile("!test")
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        if (isDatabaseInitialized()) {
            logger.info("La base de datos ya existe, omitiendo inicialización.");
        } else {
            logger.info("La base de datos está vacía. Ejecutando schema.sql...");
            executeSchema();
        }
    }

    private boolean isDatabaseInitialized() {
        // Verificamos si existe alguna tabla core, por ejemplo 'usuarios' o 'estados'
        // Adaptar 'usuarios' a una tabla que seguro deba existir.
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'usuarios'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null && count > 0;
    }

    @SuppressWarnings("null")
    private void executeSchema() throws SQLException {
        Resource resource = new ClassPathResource("schema.sql");
        if (resource.exists()) {
            try (Connection connection = dataSource.getConnection()) {
                ScriptUtils.executeSqlScript(connection, resource);
                logger.info("Inicialización de base de datos completada exitosamente.");
            }
        } else {
            logger.warn("No se encontró el archivo schema.sql en el classpath.");
        }
    }
}
