package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CaracteristicaVivienda;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CaracteristicaViviendaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CaracteristicaViviendaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CaracteristicaVivienda> rowMapper = (rs, rowNum) -> new CaracteristicaVivienda(
            rs.getString("cedula"),
            rs.getInt("id_tipo_cat"),
            rs.getInt("id_cat_vivienda"));

    public List<CaracteristicaVivienda> findAll() {
        String sql = "SELECT * FROM caracteristicas_viviendas";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<CaracteristicaVivienda> findById(String cedula, Integer idTipoCat, Integer idCatVivienda) {
        String sql = "SELECT * FROM caracteristicas_viviendas WHERE cedula = ? AND id_tipo_cat = ? AND id_cat_vivienda = ?";
        List<CaracteristicaVivienda> results = jdbcTemplate.query(sql, rowMapper, cedula, idTipoCat, idCatVivienda);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int save(CaracteristicaVivienda caracteristica) {
        String sql = "INSERT INTO caracteristicas_viviendas (cedula, id_tipo_cat, id_cat_vivienda) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, caracteristica.getCedula(), caracteristica.getIdTipoCat(),
                caracteristica.getIdCatVivienda());
    }

    public int delete(String cedula, Integer idTipoCat, Integer idCatVivienda) {
        String sql = "DELETE FROM caracteristicas_viviendas WHERE cedula = ? AND id_tipo_cat = ? AND id_cat_vivienda = ?";
        return jdbcTemplate.update(sql, cedula, idTipoCat, idCatVivienda);
    }
}
