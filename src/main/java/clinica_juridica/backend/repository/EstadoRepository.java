package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Estado;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class EstadoRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstadoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Estado> rowMapper = (rs, rowNum) -> new Estado(
            rs.getInt("id_estado"),
            rs.getString("nombre_estado"));

    public List<Estado> findAll() {
        String sql = "SELECT * FROM estados";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Estado> findById(Integer id) {
        String sql = "SELECT * FROM estados WHERE id_estado = ?";
        List<Estado> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Estado estado) {
        String sql = "INSERT INTO estados (id_estado, nombre_estado) VALUES (?, ?)";
        return jdbcTemplate.update(sql, estado.getIdEstado(), estado.getNombreEstado());
    }

    public int update(Estado estado) {
        String sql = "UPDATE estados SET nombre_estado = ? WHERE id_estado = ?";
        return jdbcTemplate.update(sql, estado.getNombreEstado(), estado.getIdEstado());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM estados WHERE id_estado = ?";
        return jdbcTemplate.update(sql, id);
    }
}
