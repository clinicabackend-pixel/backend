package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Semestre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class SemestreRepository {

    private final JdbcTemplate jdbcTemplate;

    public SemestreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Semestre> rowMapper = (rs, rowNum) -> new Semestre(
            rs.getString("termino"),
            rs.getString("nombre"),
            rs.getDate("fecha_inicio").toLocalDate(),
            rs.getDate("fecha_fin").toLocalDate());

    public List<Semestre> findAll() {
        String sql = "SELECT * FROM semestre";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Semestre> findById(String termino) {
        String sql = "SELECT * FROM semestre WHERE termino = ?";
        List<Semestre> results = jdbcTemplate.query(sql, rowMapper, termino);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Semestre semestre) {
        String sql = "INSERT INTO semestre (termino, nombre, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, semestre.getTermino(), semestre.getNombre(), semestre.getFechaInicio(),
                semestre.getFechaFin());
    }

    public int update(Semestre semestre) {
        String sql = "UPDATE semestre SET nombre = ?, fecha_inicio = ?, fecha_fin = ? WHERE termino = ?";
        return jdbcTemplate.update(sql, semestre.getNombre(), semestre.getFechaInicio(), semestre.getFechaFin(),
                semestre.getTermino());
    }

    public int delete(String termino) {
        String sql = "DELETE FROM semestre WHERE termino = ?";
        return jdbcTemplate.update(sql, termino);
    }
}
