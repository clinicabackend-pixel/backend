package clinica_juridica.backend.infrastructure.adapter.persistence;

import clinica_juridica.backend.application.port.output.EstudianteRepository;
import clinica_juridica.backend.domain.entities.Estudiante;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EstudianteJdbcAdapter implements EstudianteRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstudianteJdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        return estudiante;
    }

    @Override
    public Optional<Estudiante> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Estudiante> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(String id) {
    }
}

