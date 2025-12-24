package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CondicionActividad;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CondicionActividadRepository {

    private final JdbcTemplate jdbcTemplate;

    public CondicionActividadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CondicionActividad> rowMapper = (rs, rowNum) -> new CondicionActividad(
            rs.getInt("id_condicion_actividad"),
            rs.getString("nombre_actividad"),
            rs.getString("estatus"));

    public List<CondicionActividad> findAll() {
        String sql = "SELECT * FROM condicion_actividad";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<CondicionActividad> findById(Integer id) {
        String sql = "SELECT * FROM condicion_actividad WHERE id_condicion_actividad = ?";
        List<CondicionActividad> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(CondicionActividad actividad) {
        String sql = "INSERT INTO condicion_actividad (nombre_actividad, estatus) VALUES (?, ?)";
        return jdbcTemplate.update(sql, actividad.getNombreActividad(), actividad.getEstatus());
    }

    public int update(CondicionActividad actividad) {
        String sql = "UPDATE condicion_actividad SET nombre_actividad = ?, estatus = ? WHERE id_condicion_actividad = ?";
        return jdbcTemplate.update(sql, actividad.getNombreActividad(), actividad.getEstatus(),
                actividad.getIdCondicionActividad());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM condicion_actividad WHERE id_condicion_actividad = ?";
        return jdbcTemplate.update(sql, id);
    }
}
