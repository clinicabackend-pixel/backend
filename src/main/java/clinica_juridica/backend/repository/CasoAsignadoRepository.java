package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CasoAsignado;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CasoAsignadoRepository {

    private final JdbcTemplate jdbcTemplate;

    public CasoAsignadoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CasoAsignado> rowMapper = (rs, rowNum) -> new CasoAsignado(
            rs.getString("num_caso"),
            rs.getString("username"),
            rs.getString("termino"));

    public List<CasoAsignado> findAll() {
        String sql = "SELECT * FROM casos_asignados";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<CasoAsignado> findById(String numCaso, String username, String termino) {
        String sql = "SELECT * FROM casos_asignados WHERE num_caso = ? AND username = ? AND termino = ?";
        List<CasoAsignado> results = jdbcTemplate.query(sql, rowMapper, numCaso, username, termino);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(CasoAsignado asignacion) {
        String sql = "INSERT INTO casos_asignados (num_caso, username, termino) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, asignacion.getNumCaso(), asignacion.getUsername(), asignacion.getTermino());
    }

    public int delete(String numCaso, String username, String termino) {
        String sql = "DELETE FROM casos_asignados WHERE num_caso = ? AND username = ? AND termino = ?";
        return jdbcTemplate.update(sql, numCaso, username, termino);
    }
}
