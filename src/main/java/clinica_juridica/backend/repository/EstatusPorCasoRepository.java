package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.EstatusPorCaso;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class EstatusPorCasoRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstatusPorCasoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<EstatusPorCaso> rowMapper = (rs, rowNum) -> new EstatusPorCaso(
            rs.getInt("id_est_caso"),
            rs.getString("num_caso"),
            rs.getDate("fecha_cambio") != null ? rs.getDate("fecha_cambio").toLocalDate() : null,
            rs.getString("status_ant"));

    public List<EstatusPorCaso> findAll() {
        String sql = "SELECT * FROM estatus_por_caso";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<EstatusPorCaso> findById(Integer id) {
        String sql = "SELECT * FROM estatus_por_caso WHERE id_est_caso = ?";
        List<EstatusPorCaso> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(EstatusPorCaso estatus) {
        String sql = "INSERT INTO estatus_por_caso (num_caso, fecha_cambio, status_ant) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, estatus.getNumCaso(), estatus.getFechaCambio(), estatus.getStatusAnt());
    }

    public int update(EstatusPorCaso estatus) {
        String sql = "UPDATE estatus_por_caso SET num_caso = ?, fecha_cambio = ?, status_ant = ? WHERE id_est_caso = ?";
        return jdbcTemplate.update(sql, estatus.getNumCaso(), estatus.getFechaCambio(), estatus.getStatusAnt(),
                estatus.getIdEstCaso());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM estatus_por_caso WHERE id_est_caso = ?";
        return jdbcTemplate.update(sql, id);
    }
}
