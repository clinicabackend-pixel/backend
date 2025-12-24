package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Profesor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class ProfesorRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProfesorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Profesor> rowMapper = (rs, rowNum) -> new Profesor(
            rs.getString("username"),
            rs.getString("termino"));

    public List<Profesor> findAll() {
        String sql = "SELECT * FROM profesores";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Profesor> findById(String username) {
        String sql = "SELECT * FROM profesores WHERE username = ?";
        List<Profesor> results = jdbcTemplate.query(sql, rowMapper, username);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Profesor profesor) {
        String sql = "INSERT INTO profesores (username, termino) VALUES (?, ?)";
        return jdbcTemplate.update(sql, profesor.getUsername(), profesor.getTermino());
    }

    public int update(Profesor profesor) {
        String sql = "UPDATE profesores SET termino = ? WHERE username = ?";
        return jdbcTemplate.update(sql, profesor.getTermino(), profesor.getUsername());
    }

    public int delete(String username) {
        String sql = "DELETE FROM profesores WHERE username = ?";
        return jdbcTemplate.update(sql, username);
    }
}
