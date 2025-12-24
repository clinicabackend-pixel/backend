package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Tribunal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class TribunalRepository {

    private final JdbcTemplate jdbcTemplate;

    public TribunalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Tribunal> rowMapper = (rs, rowNum) -> new Tribunal(
            rs.getInt("id_tribunal"),
            rs.getString("nombre_tribunal"),
            rs.getString("materia"),
            rs.getString("instancia"),
            rs.getString("ubicacion"),
            rs.getString("estatus"));

    public List<Tribunal> findAll() {
        String sql = "SELECT * FROM tribunal";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Tribunal> findById(Integer id) {
        String sql = "SELECT * FROM tribunal WHERE id_tribunal = ?";
        List<Tribunal> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Tribunal tribunal) {
        String sql = "INSERT INTO tribunal (id_tribunal, nombre_tribunal, materia, instancia, ubicacion, estatus) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, tribunal.getIdTribunal(), tribunal.getNombreTribunal(), tribunal.getMateria(),
                tribunal.getInstancia(), tribunal.getUbicacion(), tribunal.getEstatus());
    }

    public int update(Tribunal tribunal) {
        String sql = "UPDATE tribunal SET nombre_tribunal = ?, materia = ?, instancia = ?, ubicacion = ?, estatus = ? WHERE id_tribunal = ?";
        return jdbcTemplate.update(sql, tribunal.getNombreTribunal(), tribunal.getMateria(), tribunal.getInstancia(),
                tribunal.getUbicacion(), tribunal.getEstatus(), tribunal.getIdTribunal());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM tribunal WHERE id_tribunal = ?";
        return jdbcTemplate.update(sql, id);
    }
}
