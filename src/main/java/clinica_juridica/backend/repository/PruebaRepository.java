package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Prueba;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PruebaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PruebaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Prueba> rowMapper = (rs, rowNum) -> new Prueba(
            rs.getInt("id_prueba"),
            rs.getString("num_caso"),
            rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null,
            rs.getString("documento"),
            rs.getString("observacion"),
            rs.getString("titulo"));

    public List<Prueba> findAll() {
        String sql = "SELECT * FROM pruebas";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Prueba> findById(Integer id) {
        String sql = "SELECT * FROM pruebas WHERE id_prueba = ?";
        List<Prueba> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Prueba prueba) {
        String sql = "INSERT INTO pruebas (num_caso, fecha, documento, observacion, titulo) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, prueba.getNumCaso(), prueba.getFecha(), prueba.getDocumento(),
                prueba.getObservacion(), prueba.getTitulo());
    }

    public int update(Prueba prueba) {
        String sql = "UPDATE pruebas SET num_caso = ?, fecha = ?, documento = ?, observacion = ?, titulo = ? WHERE id_prueba = ?";
        return jdbcTemplate.update(sql, prueba.getNumCaso(), prueba.getFecha(), prueba.getDocumento(),
                prueba.getObservacion(), prueba.getTitulo(), prueba.getIdPrueba());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM pruebas WHERE id_prueba = ?";
        return jdbcTemplate.update(sql, id);
    }
}
