package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Encuentro;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EncuentroRepository {

    private final JdbcTemplate jdbcTemplate;

    public EncuentroRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Encuentro> rowMapper = (rs, rowNum) -> new Encuentro(
            rs.getInt("id_encuentros"),
            rs.getString("num_caso"),
            rs.getDate("fecha_atencion") != null ? rs.getDate("fecha_atencion").toLocalDate() : null,
            rs.getDate("fecha_proxima") != null ? rs.getDate("fecha_proxima").toLocalDate() : null,
            rs.getString("orientacion"),
            rs.getString("observacion"),
            rs.getString("username"));

    public List<Encuentro> findAll() {
        String sql = "SELECT * FROM encuentros";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Encuentro> findById(Integer id) {
        String sql = "SELECT * FROM encuentros WHERE id_encuentros = ?";
        List<Encuentro> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Encuentro encuentro) {
        String sql = "INSERT INTO encuentros (num_caso, fecha_atencion, fecha_proxima, orientacion, observacion, username) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, encuentro.getNumCaso(), encuentro.getFechaAtencion(),
                encuentro.getFechaProxima(), encuentro.getOrientacion(), encuentro.getObservacion(),
                encuentro.getUsername());
    }

    public int update(Encuentro encuentro) {
        String sql = "UPDATE encuentros SET num_caso = ?, fecha_atencion = ?, fecha_proxima = ?, orientacion = ?, observacion = ?, username = ? WHERE id_encuentros = ?";
        return jdbcTemplate.update(sql, encuentro.getNumCaso(), encuentro.getFechaAtencion(),
                encuentro.getFechaProxima(), encuentro.getOrientacion(), encuentro.getObservacion(),
                encuentro.getUsername(), encuentro.getIdEncuentros());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM encuentros WHERE id_encuentros = ?";
        return jdbcTemplate.update(sql, id);
    }
}
