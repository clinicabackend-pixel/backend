package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Estudiante;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class EstudianteRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstudianteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Estudiante> rowMapper = (rs, rowNum) -> new Estudiante(
            rs.getString("username"),
            rs.getString("termino"),
            rs.getString("tipo_de_estudiante"),
            rs.getInt("nrc"));

    public List<Estudiante> findAll() {
        String sql = "SELECT * FROM estudiantes";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Estudiante> findById(String username, String termino) {
        String sql = "SELECT * FROM estudiantes WHERE username = ? AND termino = ?";
        List<Estudiante> results = jdbcTemplate.query(sql, rowMapper, username, termino);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (username, termino, tipo_de_estudiante, nrc) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, estudiante.getUsername(), estudiante.getTermino(),
                estudiante.getTipoDeEstudiante(), estudiante.getNrc());
    }

    public int update(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET tipo_de_estudiante = ?, nrc = ? WHERE username = ? AND termino = ?";
        return jdbcTemplate.update(sql, estudiante.getTipoDeEstudiante(), estudiante.getNrc(), estudiante.getUsername(),
                estudiante.getTermino());
    }

    public int delete(String username, String termino) {
        String sql = "DELETE FROM estudiantes WHERE username = ? AND termino = ?";
        return jdbcTemplate.update(sql, username, termino);
    }
}
