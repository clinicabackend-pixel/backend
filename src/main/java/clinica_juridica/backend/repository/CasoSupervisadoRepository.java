package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CasoSupervisado;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CasoSupervisadoRepository {

    private final JdbcTemplate jdbcTemplate;

    public CasoSupervisadoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CasoSupervisado> rowMapper = (rs, rowNum) -> new CasoSupervisado(
            rs.getString("num_caso"),
            rs.getString("username"),
            rs.getString("termino"));

    public List<CasoSupervisado> findAll() {
        String sql = "SELECT * FROM casos_supervisados";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<CasoSupervisado> findById(String numCaso, String username, String termino) {
        String sql = "SELECT * FROM casos_supervisados WHERE num_caso = ? AND username = ? AND termino = ?";
        List<CasoSupervisado> results = jdbcTemplate.query(sql, rowMapper, numCaso, username, termino);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(CasoSupervisado supervision) {
        String sql = "INSERT INTO casos_supervisados (num_caso, username, termino) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, supervision.getNumCaso(), supervision.getUsername(), supervision.getTermino());
    }

    public int delete(String numCaso, String username, String termino) {
        String sql = "DELETE FROM casos_supervisados WHERE num_caso = ? AND username = ? AND termino = ?";
        return jdbcTemplate.update(sql, numCaso, username, termino);
    }
}
