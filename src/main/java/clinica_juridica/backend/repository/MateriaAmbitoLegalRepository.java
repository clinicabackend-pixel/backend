package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.MateriaAmbitoLegal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class MateriaAmbitoLegalRepository {

    private final JdbcTemplate jdbcTemplate;

    public MateriaAmbitoLegalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<MateriaAmbitoLegal> rowMapper = (rs, rowNum) -> new MateriaAmbitoLegal(
            rs.getInt("cod_mat_amb_legal"),
            rs.getString("mat_amb_legal"));

    public List<MateriaAmbitoLegal> findAll() {
        String sql = "SELECT * FROM materia_ambito_legal";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<MateriaAmbitoLegal> findById(Integer id) {
        String sql = "SELECT * FROM materia_ambito_legal WHERE cod_mat_amb_legal = ?";
        List<MateriaAmbitoLegal> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(MateriaAmbitoLegal materia) {
        String sql = "INSERT INTO materia_ambito_legal (cod_mat_amb_legal, mat_amb_legal) VALUES (?, ?)";
        return jdbcTemplate.update(sql, materia.getCodMatAmbLegal(), materia.getMatAmbLegal());
    }

    public int update(MateriaAmbitoLegal materia) {
        String sql = "UPDATE materia_ambito_legal SET mat_amb_legal = ? WHERE cod_mat_amb_legal = ?";
        return jdbcTemplate.update(sql, materia.getMatAmbLegal(), materia.getCodMatAmbLegal());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM materia_ambito_legal WHERE cod_mat_amb_legal = ?";
        return jdbcTemplate.update(sql, id);
    }
}
