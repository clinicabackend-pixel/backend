package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Parroquia;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ParroquiaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ParroquiaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Parroquia> rowMapper = (rs, rowNum) -> new Parroquia(
            rs.getInt("id_parroquia"),
            rs.getString("parroquia"),
            rs.getInt("id_municipio"));

    public List<Parroquia> findAll() {
        String sql = "SELECT * FROM parroquias";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Parroquia> findById(Integer id) {
        String sql = "SELECT * FROM parroquias WHERE id_parroquia = ?";
        List<Parroquia> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Parroquia parroquia) {
        String sql = "INSERT INTO parroquias (id_parroquia, parroquia, id_municipio) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, parroquia.getIdParroquia(), parroquia.getParroquia(),
                parroquia.getIdMunicipio());
    }

    public int update(Parroquia parroquia) {
        String sql = "UPDATE parroquias SET parroquia = ?, id_municipio = ? WHERE id_parroquia = ?";
        return jdbcTemplate.update(sql, parroquia.getParroquia(), parroquia.getIdMunicipio(),
                parroquia.getIdParroquia());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM parroquias WHERE id_parroquia = ?";
        return jdbcTemplate.update(sql, id);
    }
}
