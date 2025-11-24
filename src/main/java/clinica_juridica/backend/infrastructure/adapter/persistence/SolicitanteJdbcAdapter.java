package clinica_juridica.backend.infrastructure.adapter.persistence;

import clinica_juridica.backend.application.port.output.SolicitanteRepository;
import clinica_juridica.backend.domain.entities.Solicitante;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SolicitanteJdbcAdapter implements SolicitanteRepository {

    private final JdbcTemplate jdbcTemplate;

    public SolicitanteJdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Solicitante save(Solicitante solicitante) {
        return solicitante;
    }

    @Override
    public Optional<Solicitante> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Solicitante> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(String id) {
    }
}

