package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Accion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class AccionRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Accion> rowMapper = (rs, rowNum) -> new Accion(
            rs.getInt("id_accion"),
            rs.getString("num_caso"),
            rs.getString("titulo"),
            rs.getString("descripcion"),
            rs.getDate("fecha_registro") != null ? rs.getDate("fecha_registro").toLocalDate() : null,
            rs.getDate("fecha_ejecucion") != null ? rs.getDate("fecha_ejecucion").toLocalDate() : null,
            rs.getString("username"));

    public List<Accion> findAll() {
        String sql = "SELECT * FROM accion";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Accion> findById(Integer id) {
        String sql = "SELECT * FROM accion WHERE id_accion = ?";
        List<Accion> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Accion accion) {
        String sql = "INSERT INTO accion (num_caso, titulo, descripcion, fecha_registro, fecha_ejecucion, username) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, accion.getNumCaso(), accion.getTitulo(), accion.getDescripcion(),
                accion.getFechaRegistro(), accion.getFechaEjecucion(), accion.getUsername());
    }

    public int update(Accion accion) {
        String sql = "UPDATE accion SET num_caso = ?, titulo = ?, descripcion = ?, fecha_registro = ?, fecha_ejecucion = ?, username = ? WHERE id_accion = ?";
        return jdbcTemplate.update(sql, accion.getNumCaso(), accion.getTitulo(), accion.getDescripcion(),
                accion.getFechaRegistro(), accion.getFechaEjecucion(), accion.getUsername(), accion.getIdAccion());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM accion WHERE id_accion = ?";
        return jdbcTemplate.update(sql, id);
    }
}
