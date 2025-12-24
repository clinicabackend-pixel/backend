package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Solicitante;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class SolicitanteRepository {

    private final JdbcTemplate jdbcTemplate;

    public SolicitanteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Solicitante> rowMapper = new RowMapper<>() {
        @Override
        public Solicitante mapRow(ResultSet rs, int rowNum) throws SQLException {
            Solicitante solicitante = new Solicitante();
            solicitante.setCedula(rs.getString("cedula"));
            solicitante.setNombre(rs.getString("nombre"));
            solicitante.setNacionalidad(rs.getString("nacionalidad"));
            solicitante.setSexo(rs.getString("sexo"));
            solicitante.setEmail(rs.getString("email"));
            solicitante.setConcubinato(rs.getString("concubinato"));
            solicitante.setIdEstadoCivil(rs.getObject("id_estado_civil", Integer.class));
            solicitante.setTelfCelular(rs.getString("telf_celular"));
            solicitante.setTelfCasa(rs.getString("telf_casa"));
            solicitante.setFNacimiento(
                    rs.getDate("f_nacimiento") != null ? rs.getDate("f_nacimiento").toLocalDate() : null);
            solicitante.setEdad(rs.getObject("edad", Integer.class));
            solicitante.setIdCondicion(rs.getObject("id_condicion", Integer.class));
            solicitante.setIdCondicionActividad(rs.getObject("id_condicion_actividad", Integer.class));
            solicitante.setIdNivel(rs.getObject("id_nivel", Integer.class));
            solicitante.setTiempoEstudio(rs.getString("tiempo_estudio"));
            return solicitante;
        }
    };

    public List<Solicitante> findAll() {
        String sql = "SELECT * FROM solicitantes";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Solicitante> findById(String cedula) {
        String sql = "SELECT * FROM solicitantes WHERE cedula = ?";
        List<Solicitante> result = jdbcTemplate.query(sql, rowMapper, cedula);
        return result.stream().findFirst();
    }

    public int save(Solicitante s) {
        String sql = "INSERT INTO solicitantes (cedula, nombre, nacionalidad, sexo, email, concubinato, id_estado_civil, "
                +
                "telf_celular, telf_casa, f_nacimiento, edad, id_condicion, id_condicion_actividad, id_nivel, tiempo_estudio) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                s.getCedula(), s.getNombre(), s.getNacionalidad(), s.getSexo(), s.getEmail(), s.getConcubinato(),
                s.getIdEstadoCivil(), s.getTelfCelular(), s.getTelfCasa(), s.getFNacimiento(), s.getEdad(),
                s.getIdCondicion(), s.getIdCondicionActividad(), s.getIdNivel(), s.getTiempoEstudio());
    }

    public int update(Solicitante s) {
        String sql = "UPDATE solicitantes SET nombre = ?, nacionalidad = ?, sexo = ?, email = ?, concubinato = ?, " +
                "id_estado_civil = ?, telf_celular = ?, telf_casa = ?, f_nacimiento = ?, edad = ?, " +
                "id_condicion = ?, id_condicion_actividad = ?, id_nivel = ?, tiempo_estudio = ? WHERE cedula = ?";
        return jdbcTemplate.update(sql,
                s.getNombre(), s.getNacionalidad(), s.getSexo(), s.getEmail(), s.getConcubinato(),
                s.getIdEstadoCivil(), s.getTelfCelular(), s.getTelfCasa(), s.getFNacimiento(), s.getEdad(),
                s.getIdCondicion(), s.getIdCondicionActividad(), s.getIdNivel(), s.getTiempoEstudio(), s.getCedula());
    }

    public int delete(String cedula) {
        String sql = "DELETE FROM solicitantes WHERE cedula = ?";
        return jdbcTemplate.update(sql, cedula);
    }
}
