package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Municipio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class MunicipioRepository {

    private final JdbcTemplate jdbcTemplate;

    public MunicipioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Municipio> rowMapper = (rs, rowNum) -> new Municipio(
            rs.getInt("id_municipio"),
            rs.getObject("id_estado", Integer.class),
            rs.getString("nombre_municipio"));

    public List<Municipio> findAll() {
        String sql = "SELECT * FROM municipios";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Municipio> findById(Integer id) {
        String sql = "SELECT * FROM municipios WHERE id_municipio = ?";
        List<Municipio> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Municipio municipio) {
        String sql = "INSERT INTO municipios (id_municipio, id_estado, nombre_municipio) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, municipio.getIdMunicipio(), municipio.getIdEstado(),
                municipio.getNombreMunicipio());
    }

    public int update(Municipio municipio) {
        String sql = "UPDATE municipios SET id_estado = ?, nombre_municipio = ? WHERE id_municipio = ?";
        return jdbcTemplate.update(sql, municipio.getIdEstado(), municipio.getNombreMunicipio(),
                municipio.getIdMunicipio());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM municipios WHERE id_municipio = ?";
        return jdbcTemplate.update(sql, id);
    }
}
