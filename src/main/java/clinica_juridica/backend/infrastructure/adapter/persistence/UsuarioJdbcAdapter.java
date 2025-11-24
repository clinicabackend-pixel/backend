package clinica_juridica.backend.infrastructure.adapter.persistence;

import clinica_juridica.backend.application.port.output.UsuarioRepository;
import clinica_juridica.backend.domain.entities.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioJdbcAdapter implements UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioJdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public List<Usuario> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(String id) {
    }
}

