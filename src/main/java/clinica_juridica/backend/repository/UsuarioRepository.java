package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> new Usuario(
            rs.getString("username"),
            rs.getString("cedula"),
            rs.getString("contrasena"),
            rs.getString("nombre"),
            rs.getString("email"),
            rs.getString("status"),
            rs.getString("tipo"));

    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuarios";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Usuario> findById(String username) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        List<Usuario> results = jdbcTemplate.query(sql, rowMapper, username);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<Usuario> findByUsername(String username) {
        return findById(username);
    }

    public int save(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, cedula, contrasena, nombre, email, status, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, usuario.getUsername(), usuario.getCedula(), usuario.getContrasena(),
                usuario.getNombre(), usuario.getEmail(), usuario.getStatus(), usuario.getTipo());
    }

    public int update(Usuario usuario) {
        String sql = "UPDATE usuarios SET cedula = ?, contrasena = ?, nombre = ?, email = ?, status = ?, tipo = ? WHERE username = ?";
        return jdbcTemplate.update(sql, usuario.getCedula(), usuario.getContrasena(), usuario.getNombre(),
                usuario.getEmail(), usuario.getStatus(), usuario.getTipo(), usuario.getUsername());
    }

    public int delete(String username) {
        String sql = "DELETE FROM usuarios WHERE username = ?";
        return jdbcTemplate.update(sql, username);
    }
}
