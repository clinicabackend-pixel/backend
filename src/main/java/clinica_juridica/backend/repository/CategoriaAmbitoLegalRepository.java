package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CategoriaAmbitoLegal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class CategoriaAmbitoLegalRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaAmbitoLegalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CategoriaAmbitoLegal> rowMapper = (rs, rowNum) -> new CategoriaAmbitoLegal(
            rs.getInt("cod_cat_amb_legal"),
            rs.getInt("cod_mat_amb_legal"),
            rs.getString("cat_amb_legal"));

    public List<CategoriaAmbitoLegal> findAll() {
        String sql = "SELECT * FROM categoria_ambito_legal";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<CategoriaAmbitoLegal> findById(Integer id) {
        String sql = "SELECT * FROM categoria_ambito_legal WHERE cod_cat_amb_legal = ?";
        List<CategoriaAmbitoLegal> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(CategoriaAmbitoLegal categoria) {
        String sql = "INSERT INTO categoria_ambito_legal (cod_cat_amb_legal, cod_mat_amb_legal, cat_amb_legal) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, categoria.getCodCatAmbLegal(), categoria.getCodMatAmbLegal(),
                categoria.getCatAmbLegal());
    }

    public int update(CategoriaAmbitoLegal categoria) {
        String sql = "UPDATE categoria_ambito_legal SET cod_mat_amb_legal = ?, cat_amb_legal = ? WHERE cod_cat_amb_legal = ?";
        return jdbcTemplate.update(sql, categoria.getCodMatAmbLegal(), categoria.getCatAmbLegal(),
                categoria.getCodCatAmbLegal());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM categoria_ambito_legal WHERE cod_cat_amb_legal = ?";
        return jdbcTemplate.update(sql, id);
    }
}
