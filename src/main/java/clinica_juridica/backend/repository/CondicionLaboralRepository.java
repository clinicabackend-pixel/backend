package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CondicionLaboral;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class CondicionLaboralRepository {

    private final JdbcTemplate jdbcTemplate;

    public CondicionLaboralRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CondicionLaboral> rowMapper = (rs, rowNum) -> new CondicionLaboral(
            rs.getInt("id_condicion"),
            rs.getString("condicion"),
            rs.getString("estatus"));

    public List<CondicionLaboral> findAll() {
        String sql = "SELECT * FROM condicion_laboral";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<CondicionLaboral> findById(Integer id) {
        String sql = "SELECT * FROM condicion_laboral WHERE id_condicion = ?";
        List<CondicionLaboral> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(CondicionLaboral condicion) {
        String sql = "INSERT INTO condicion_laboral (condicion, estatus) VALUES (?, ?)";
        return jdbcTemplate.update(sql, condicion.getCondicion(), condicion.getEstatus());
    }

    public int update(CondicionLaboral condicion) {
        String sql = "UPDATE condicion_laboral SET condicion = ?, estatus = ? WHERE id_condicion = ?";
        return jdbcTemplate.update(sql, condicion.getCondicion(), condicion.getEstatus(), condicion.getIdCondicion());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM condicion_laboral WHERE id_condicion = ?";
        return jdbcTemplate.update(sql, id);
    }
}
