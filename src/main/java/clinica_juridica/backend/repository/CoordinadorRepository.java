package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Coordinador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class CoordinadorRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoordinadorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Coordinador> rowMapper = (rs, rowNum) -> new Coordinador(
            rs.getString("username"));

    public List<Coordinador> findAll() {
        String sql = "SELECT * FROM coordinadores";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Coordinador> findById(String username) {
        String sql = "SELECT * FROM coordinadores WHERE username = ?";
        List<Coordinador> results = jdbcTemplate.query(sql, rowMapper, username);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Coordinador coordinador) {
        String sql = "INSERT INTO coordinadores (username) VALUES (?)";
        return jdbcTemplate.update(sql, coordinador.getUsername());
    }

    public int delete(String username) {
        String sql = "DELETE FROM coordinadores WHERE username = ?";
        return jdbcTemplate.update(sql, username);
    }
}
