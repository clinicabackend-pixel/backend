package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.NivelEducativo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class NivelEducativoRepository {

    private final JdbcTemplate jdbcTemplate;

    public NivelEducativoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<NivelEducativo> rowMapper = (rs, rowNum) -> new NivelEducativo(
            rs.getInt("id_nivel_edu"),
            rs.getString("nivel"),
            rs.getObject("anio", Integer.class));

    public List<NivelEducativo> findAll() {
        String sql = "SELECT * FROM niveles_educativos";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<NivelEducativo> findById(Integer id) {
        String sql = "SELECT * FROM niveles_educativos WHERE id_nivel_edu = ?";
        List<NivelEducativo> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(NivelEducativo nivelEducativo) {
        String sql = "INSERT INTO niveles_educativos (id_nivel_edu, nivel, anio) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, nivelEducativo.getIdNivelEdu(), nivelEducativo.getNivel(),
                nivelEducativo.getAnio());
    }

    public int update(NivelEducativo nivelEducativo) {
        String sql = "UPDATE niveles_educativos SET nivel = ?, anio = ? WHERE id_nivel_edu = ?";
        return jdbcTemplate.update(sql, nivelEducativo.getNivel(), nivelEducativo.getAnio(),
                nivelEducativo.getIdNivelEdu());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM niveles_educativos WHERE id_nivel_edu = ?";
        return jdbcTemplate.update(sql, id);
    }
}
