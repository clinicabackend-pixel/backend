package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.SubcategoriaAmbitoLegal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubcategoriaAmbitoLegalRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubcategoriaAmbitoLegalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<SubcategoriaAmbitoLegal> rowMapper = (rs, rowNum) -> new SubcategoriaAmbitoLegal(
            rs.getInt("cod_sub_amb_legal"),
            rs.getInt("cod_cat_amb_legal"),
            rs.getString("nombre_subcategoria"));

    public List<SubcategoriaAmbitoLegal> findAll() {
        String sql = "SELECT * FROM subcategoria_ambito_legal";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<SubcategoriaAmbitoLegal> findById(Integer id) {
        String sql = "SELECT * FROM subcategoria_ambito_legal WHERE cod_sub_amb_legal = ?";
        List<SubcategoriaAmbitoLegal> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(SubcategoriaAmbitoLegal subcategoria) {
        String sql = "INSERT INTO subcategoria_ambito_legal (cod_sub_amb_legal, cod_cat_amb_legal, nombre_subcategoria) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, subcategoria.getCodSubAmbLegal(), subcategoria.getCodCatAmbLegal(),
                subcategoria.getNombreSubcategoria());
    }

    public int update(SubcategoriaAmbitoLegal subcategoria) {
        String sql = "UPDATE subcategoria_ambito_legal SET cod_cat_amb_legal = ?, nombre_subcategoria = ? WHERE cod_sub_amb_legal = ?";
        return jdbcTemplate.update(sql, subcategoria.getCodCatAmbLegal(), subcategoria.getNombreSubcategoria(),
                subcategoria.getCodSubAmbLegal());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM subcategoria_ambito_legal WHERE cod_sub_amb_legal = ?";
        return jdbcTemplate.update(sql, id);
    }
}
