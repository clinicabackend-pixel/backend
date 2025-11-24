package clinica_juridica.backend.infrastructure.adapter.persistence;

import clinica_juridica.backend.application.port.output.SeccionRepository;
import clinica_juridica.backend.domain.entities.Seccion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SeccionJdbcAdapter implements SeccionRepository {

    private final JdbcTemplate jdbcTemplate;

    public SeccionJdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Seccion save(Seccion seccion) {
        return seccion;
    }

    @Override
    public Optional<Seccion> findById(Integer idMateria, Integer idSeccion, Integer idSemestre) {
        return Optional.empty();
    }

    @Override
    public List<Seccion> findAll() {
        return List.of();
    }

    @Override
    public void delete(Integer idMateria, Integer idSeccion, Integer idSemestre) {
    }
}

