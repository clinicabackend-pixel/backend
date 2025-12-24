package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.EncuentroAtendido;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EncuentroAtendidoRepository {

    private final JdbcTemplate jdbcTemplate;

    public EncuentroAtendidoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<EncuentroAtendido> rowMapper = (rs, rowNum) -> new EncuentroAtendido(
            rs.getInt("id_encuentro_atendido"),
            rs.getInt("id_encuentro"),
            rs.getString("num_caso"),
            rs.getString("username"));

    public List<EncuentroAtendido> findAll() {
        String sql = "SELECT * FROM encuentros_atendidos";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<EncuentroAtendido> findById(Integer id) {
        String sql = "SELECT * FROM encuentros_atendidos WHERE id_encuentro_atendido = ?";
        List<EncuentroAtendido> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(EncuentroAtendido atendido) {
        String sql = "INSERT INTO encuentros_atendidos (id_encuentro, num_caso, username) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, atendido.getIdEncuentro(), atendido.getNumCaso(), atendido.getUsername());
    }

    public int update(EncuentroAtendido atendido) {
        String sql = "UPDATE encuentros_atendidos SET id_encuentro = ?, num_caso = ?, username = ? WHERE id_encuentro_atendido = ?";
        return jdbcTemplate.update(sql, atendido.getIdEncuentro(), atendido.getNumCaso(), atendido.getUsername(),
                atendido.getIdEncuentroAtendido());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM encuentros_atendidos WHERE id_encuentro_atendido = ?";
        return jdbcTemplate.update(sql, id);
    }
}
