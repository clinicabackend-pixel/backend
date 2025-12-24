package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Vivienda;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class ViviendaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ViviendaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Vivienda> rowMapper = (rs, rowNum) -> new Vivienda(
            rs.getString("id_vivienda"),
            rs.getString("tipo"),
            rs.getObject("cant_habitaciones", Integer.class),
            rs.getObject("cant_banos", Integer.class),
            rs.getString("material_paredes"),
            rs.getString("aguas_negras"),
            rs.getString("servicio_agua"),
            rs.getString("material_techo"),
            rs.getString("material_piso"),
            rs.getString("servicio_aseo"));

    public List<Vivienda> findAll() {
        String sql = "SELECT * FROM viviendas";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Vivienda> findById(String id) {
        String sql = "SELECT * FROM viviendas WHERE id_vivienda = ?";
        List<Vivienda> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Vivienda vivienda) {
        String sql = "INSERT INTO viviendas (id_vivienda, tipo, cant_habitaciones, cant_banos, material_paredes, aguas_negras, servicio_agua, material_techo, material_piso, servicio_aseo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, vivienda.getIdVivienda(), vivienda.getTipo(), vivienda.getCantHabitaciones(),
                vivienda.getCantBanos(), vivienda.getMaterialParedes(), vivienda.getAguasNegras(),
                vivienda.getServicioAgua(), vivienda.getMaterialTecho(), vivienda.getMaterialPiso(),
                vivienda.getServicioAseo());
    }

    public int update(Vivienda vivienda) {
        String sql = "UPDATE viviendas SET tipo = ?, cant_habitaciones = ?, cant_banos = ?, material_paredes = ?, aguas_negras = ?, servicio_agua = ?, material_techo = ?, material_piso = ?, servicio_aseo = ? WHERE id_vivienda = ?";
        return jdbcTemplate.update(sql, vivienda.getTipo(), vivienda.getCantHabitaciones(), vivienda.getCantBanos(),
                vivienda.getMaterialParedes(), vivienda.getAguasNegras(), vivienda.getServicioAgua(),
                vivienda.getMaterialTecho(), vivienda.getMaterialPiso(), vivienda.getServicioAseo(),
                vivienda.getIdVivienda());
    }

    public int delete(String id) {
        String sql = "DELETE FROM viviendas WHERE id_vivienda = ?";
        return jdbcTemplate.update(sql, id);
    }
}
