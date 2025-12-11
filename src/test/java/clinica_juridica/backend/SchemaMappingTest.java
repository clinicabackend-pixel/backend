package clinica_juridica.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SchemaMappingTest {

    @Test
    void contextLoads() {
        // This test verifies that the Spring context loads successfully.
        // It helps catch mapping errors that might prevent the application from
        // starting,
        // such as duplicated columns in mappings or invalid SQL in schema.sql.
    }
}
