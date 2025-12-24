package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CategoriaVivienda;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaViviendaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaViviendaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CategoriaVivienda> rowMapper = (rs, rowNum) -> new CategoriaVivienda(
            rs.getInt("id_cat_vivienda"),
            rs.getInt("id_tipo_cat"),
            rs.getString("descripcion"),
            rs.getString("estatus"));

    public List<CategoriaVivienda> findAll() {
        String sql = "SELECT * FROM categorias_de_vivienda";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<CategoriaVivienda> findById(Integer idCatVivienda, Integer idTipoCat) {
        String sql = "SELECT * FROM categorias_de_vivienda WHERE id_cat_vivienda = ? AND id_tipo_cat = ?";
        List<CategoriaVivienda> results = jdbcTemplate.query(sql, rowMapper, idCatVivienda, idTipoCat);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(CategoriaVivienda categoria) {
        String sql = "INSERT INTO categorias_de_vivienda (id_cat_vivienda, id_tipo_cat, descripcion, estatus) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, categoria.getIdCatVivienda(), categoria.getIdTipoCat(),
                categoria.getDescripcion(), categoria.getEstatus());
    }

    public int update(CategoriaVivienda categoria) {
        String sql = "UPDATE categorias_de_vivienda SET descripcion = ?, estatus = ? WHERE id_cat_vivienda = ? AND id_tipo_cat = ?";
        return jdbcTemplate.update(sql, categoria.getDescripcion(), categoria.getEstatus(),
                categoria.getIdCatVivienda(), categoria.getIdTipoCat());
    }

    public int delete(Integer idCatVivienda, Integer idTipoCat) {
        String sql = "DELETE FROM categorias_de_vivienda WHERE id_cat_vivienda = ? AND id_tipo_cat = ?";
        return jdbcTemplate.update(sql, idCatVivienda, idTipoCat);
    }
}
