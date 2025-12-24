package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Familia;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class FamiliaRepository {

    private final JdbcTemplate jdbcTemplate;

    public FamiliaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Familia> rowMapper = (rs, rowNum) -> new Familia(
            rs.getString("cedula"),
            rs.getObject("cant_personas", Integer.class),
            rs.getObject("cant_estudiando", Integer.class),
            rs.getBigDecimal("ingreso_mes"),
            rs.getObject("jefe_familia", Boolean.class),
            rs.getObject("cant_sin_trabajo", Integer.class),
            rs.getObject("cant_ninos", Integer.class),
            rs.getObject("cant_trabaja", Integer.class),
            rs.getObject("id_nivel_edu_jefe", Integer.class),
            rs.getString("tiempo_estudio"));

    public List<Familia> findAll() {
        String sql = "SELECT * FROM familias";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Familia> findById(String cedula) {
        String sql = "SELECT * FROM familias WHERE cedula = ?";
        List<Familia> results = jdbcTemplate.query(sql, rowMapper, cedula);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(Familia familia) {
        String sql = "INSERT INTO familias (cedula, cant_personas, cant_estudiando, ingreso_mes, jefe_familia, cant_sin_trabajo, cant_ninos, cant_trabaja, id_nivel_edu_jefe, tiempo_estudio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, familia.getCedula(), familia.getCantPersonas(), familia.getCantEstudiando(),
                familia.getIngresoMes(), familia.getJefeFamilia(), familia.getCantSinTrabajo(), familia.getCantNinos(),
                familia.getCantTrabaja(), familia.getIdNivelEduJefe(), familia.getTiempoEstudio());
    }

    public int update(Familia familia) {
        String sql = "UPDATE familias SET cant_personas = ?, cant_estudiando = ?, ingreso_mes = ?, jefe_familia = ?, cant_sin_trabajo = ?, cant_ninos = ?, cant_trabaja = ?, id_nivel_edu_jefe = ?, tiempo_estudio = ? WHERE cedula = ?";
        return jdbcTemplate.update(sql, familia.getCantPersonas(), familia.getCantEstudiando(), familia.getIngresoMes(),
                familia.getJefeFamilia(), familia.getCantSinTrabajo(), familia.getCantNinos(), familia.getCantTrabaja(),
                familia.getIdNivelEduJefe(), familia.getTiempoEstudio(), familia.getCedula());
    }

    public int delete(String cedula) {
        String sql = "DELETE FROM familias WHERE cedula = ?";
        return jdbcTemplate.update(sql, cedula);
    }
}
