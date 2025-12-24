package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.TipoCategoriaVivienda;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class TipoCategoriaViviendaRepository {

    private final JdbcTemplate jdbcTemplate;

    public TipoCategoriaViviendaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<TipoCategoriaVivienda> rowMapper = (rs, rowNum) -> new TipoCategoriaVivienda(
            rs.getInt("id_tipo_cat"),
            rs.getString("tipo_categoria"));

    public List<TipoCategoriaVivienda> findAll() {
        String sql = "SELECT * FROM tipos_categorias_viviendas";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<TipoCategoriaVivienda> findById(Integer id) {
        String sql = "SELECT * FROM tipos_categorias_viviendas WHERE id_tipo_cat = ?";
        List<TipoCategoriaVivienda> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(TipoCategoriaVivienda tipo) {
        String sql = "INSERT INTO tipos_categorias_viviendas (id_tipo_cat, tipo_categoria) VALUES (?, ?)";
        return jdbcTemplate.update(sql, tipo.getIdTipoCat(), tipo.getTipoCategoria());
    }

    public int update(TipoCategoriaVivienda tipo) {
        String sql = "UPDATE tipos_categorias_viviendas SET tipo_categoria = ? WHERE id_tipo_cat = ?";
        return jdbcTemplate.update(sql, tipo.getTipoCategoria(), tipo.getIdTipoCat());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM tipos_categorias_viviendas WHERE id_tipo_cat = ?";
        return jdbcTemplate.update(sql, id);
    }
}
