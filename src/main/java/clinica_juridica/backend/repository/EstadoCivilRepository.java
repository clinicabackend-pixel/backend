package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.EstadoCivil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EstadoCivilRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstadoCivilRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<EstadoCivil> rowMapper = (rs, rowNum) -> new EstadoCivil(
            rs.getInt("id_estado_civil"),
            rs.getString("descripcion"),
            rs.getString("estatus"));

    public List<EstadoCivil> findAll() {
        String sql = "SELECT * FROM estado_civil";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<EstadoCivil> findById(Integer id) {
        String sql = "SELECT * FROM estado_civil WHERE id_estado_civil = ?";
        List<EstadoCivil> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(EstadoCivil estado) {
        String sql = "INSERT INTO estado_civil (descripcion, estatus) VALUES (?, ?)";
        return jdbcTemplate.update(sql, estado.getDescripcion(), estado.getEstatus());
    }

    public int update(EstadoCivil estado) {
        String sql = "UPDATE estado_civil SET descripcion = ?, estatus = ? WHERE id_estado_civil = ?";
        return jdbcTemplate.update(sql, estado.getDescripcion(), estado.getEstatus(), estado.getIdEstadoCivil());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM estado_civil WHERE id_estado_civil = ?";
        return jdbcTemplate.update(sql, id);
    }
}
