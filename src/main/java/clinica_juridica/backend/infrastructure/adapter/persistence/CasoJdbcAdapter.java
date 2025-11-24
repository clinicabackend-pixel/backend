package clinica_juridica.backend.infrastructure.adapter.persistence;

import clinica_juridica.backend.application.port.output.CasoRepository;
import clinica_juridica.backend.domain.entities.Caso;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CasoJdbcAdapter implements CasoRepository {

    private final JdbcTemplate jdbcTemplate;

    public CasoJdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Caso save(Caso caso) {
        // Implementar lógica de guardado/actualización
        return caso;
    }

    @Override
    public Optional<Caso> findById(String id) {
        // Implementar consulta por ID
        return Optional.empty();
    }

    @Override
    public List<Caso> findAll() {
        // Implementar consulta de todos
        return List.of();
    }

    @Override
    public void deleteById(String id) {
        // Implementar borrado
    }
}

