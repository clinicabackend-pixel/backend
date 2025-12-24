package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Centro;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class CentroRepository {

    private final JdbcTemplate jdbcTemplate;

    public CentroRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Centro> rowMapper = (rs, rowNum) -> new Centro(
            rs.getInt("id_centro"),
            rs.getString("nombre"),
            rs.getObject("id_parroquia", Integer.class));

    public List<Centro> findAll() {
        String sql = "SELECT * FROM centros";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Centro> findById(Integer id) {
        String sql = "SELECT * FROM centros WHERE id_centro = ?";
        List<Centro> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Centro centro) {
        String sql = "INSERT INTO centros (id_centro, nombre, id_parroquia) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, centro.getIdCentro(), centro.getNombre(), centro.getIdParroquia());
    }

    public int update(Centro centro) {
        String sql = "UPDATE centros SET nombre = ?, id_parroquia = ? WHERE id_centro = ?";
        return jdbcTemplate.update(sql, centro.getNombre(), centro.getIdParroquia(), centro.getIdCentro());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM centros WHERE id_centro = ?";
        return jdbcTemplate.update(sql, id);
    }
}
