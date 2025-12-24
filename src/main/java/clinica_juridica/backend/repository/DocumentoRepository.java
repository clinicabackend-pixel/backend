package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Documento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class DocumentoRepository {

    private final JdbcTemplate jdbcTemplate;

    public DocumentoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Documento> rowMapper = (rs, rowNum) -> new Documento(
            rs.getInt("id_documento"),
            rs.getString("num_caso"),
            rs.getDate("fecha_registro") != null ? rs.getDate("fecha_registro").toLocalDate() : null,
            rs.getInt("folio_ini"),
            rs.getInt("folio_fin"),
            rs.getString("titulo"),
            rs.getString("observacion"),
            rs.getString("username"));

    public List<Documento> findAll() {
        String sql = "SELECT * FROM documentos";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Documento> findById(Integer id) {
        String sql = "SELECT * FROM documentos WHERE id_documento = ?";
        List<Documento> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Documento documento) {
        String sql = "INSERT INTO documentos (num_caso, fecha_registro, folio_ini, folio_fin, titulo, observacion, username) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, documento.getNumCaso(), documento.getFechaRegistro(), documento.getFolioIni(),
                documento.getFolioFin(), documento.getTitulo(), documento.getObservacion(), documento.getUsername());
    }

    public int update(Documento documento) {
        String sql = "UPDATE documentos SET num_caso = ?, fecha_registro = ?, folio_ini = ?, folio_fin = ?, titulo = ?, observacion = ?, username = ? WHERE id_documento = ?";
        return jdbcTemplate.update(sql, documento.getNumCaso(), documento.getFechaRegistro(), documento.getFolioIni(),
                documento.getFolioFin(), documento.getTitulo(), documento.getObservacion(), documento.getUsername(),
                documento.getIdDocumento());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM documentos WHERE id_documento = ?";
        return jdbcTemplate.update(sql, id);
    }
}
