package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.AccionEjecutada;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class AccionEjecutadaRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccionEjecutadaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AccionEjecutada> rowMapper = (rs, rowNum) -> new AccionEjecutada(
            rs.getInt("id_accion_ejecutada"),
            rs.getInt("id_accion"),
            rs.getString("num_caso"),
            rs.getString("username"));

    public List<AccionEjecutada> findAll() {
        String sql = "SELECT * FROM acciones_ejecutadas";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<AccionEjecutada> findById(Integer id) {
        String sql = "SELECT * FROM acciones_ejecutadas WHERE id_accion_ejecutada = ?";
        List<AccionEjecutada> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(AccionEjecutada accion) {
        String sql = "INSERT INTO acciones_ejecutadas (id_accion, num_caso, username) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, accion.getIdAccion(), accion.getNumCaso(), accion.getUsername());
    }

    public int update(AccionEjecutada accion) {
        String sql = "UPDATE acciones_ejecutadas SET id_accion = ?, num_caso = ?, username = ? WHERE id_accion_ejecutada = ?";
        return jdbcTemplate.update(sql, accion.getIdAccion(), accion.getNumCaso(), accion.getUsername(),
                accion.getIdAccionEjecutada());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM acciones_ejecutadas WHERE id_accion_ejecutada = ?";
        return jdbcTemplate.update(sql, id);
    }
}
