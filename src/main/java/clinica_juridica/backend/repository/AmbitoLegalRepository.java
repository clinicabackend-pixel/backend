package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.AmbitoLegal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AmbitoLegalRepository {

    private final JdbcTemplate jdbcTemplate;

    public AmbitoLegalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AmbitoLegal> rowMapper = (rs, rowNum) -> new AmbitoLegal(
            rs.getInt("cod_amb_legal"),
            rs.getInt("cod_sub_amb_legal"),
            rs.getString("amb_legal"));

    public List<AmbitoLegal> findAll() {
        String sql = "SELECT * FROM ambito_legal";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<AmbitoLegal> findById(Integer id) {
        String sql = "SELECT * FROM ambito_legal WHERE cod_amb_legal = ?";
        List<AmbitoLegal> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(AmbitoLegal ambito) {
        // Serial PK, usually we omit from INSERT unless manual overrides are allowed.
        // Assuming DB auto-increments.
        // But schema.sql used SERIAL which is auto-increment.
        // If the user provided an ID, it might be for migration.
        // Safest for SERIAL is to exclude ID from insert or use `DEFAULT` if ID is
        // null.
        // Here I'll assume we let DB handle it if ID is null, but for this simple
        // repetitive pattern, I'll include it if not null logic or just exclude it for
        // standard "save new".
        // Let's stick to standard INSERT excluding SERIAL PK for creates, but wait,
        // `AmbitoLegal` model has ID.
        // I will follow the pattern of explicitly inserting if provided, or letting
        // SERIAL handle it.
        // Actually for SERIAL, best practice is `INSERT INTO table (col) VALUES (val)
        // RETURNING id`.
        // Given complexity, and typical user requirement for "raw sql", I will just
        // INSERT other fields for now.
        String sql = "INSERT INTO ambito_legal (cod_sub_amb_legal, amb_legal) VALUES (?, ?)";
        return jdbcTemplate.update(sql, ambito.getCodSubAmbLegal(), ambito.getAmbLegal());
    }

    public int update(AmbitoLegal ambito) {
        String sql = "UPDATE ambito_legal SET cod_sub_amb_legal = ?, amb_legal = ? WHERE cod_amb_legal = ?";
        return jdbcTemplate.update(sql, ambito.getCodSubAmbLegal(), ambito.getAmbLegal(), ambito.getCodAmbLegal());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM ambito_legal WHERE cod_amb_legal = ?";
        return jdbcTemplate.update(sql, id);
    }
}
