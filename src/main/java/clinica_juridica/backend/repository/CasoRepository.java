package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Caso;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("null")
public class CasoRepository {

        private final JdbcTemplate jdbcTemplate;

        public CasoRepository(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;
        }

        private final RowMapper<Caso> rowMapper = new RowMapper<>() {
                @Override
                public Caso mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Caso caso = new Caso();
                        caso.setNumCaso(rs.getString("num_caso"));
                        caso.setFechaRecepcion(rs.getDate("fecha_recepcion") != null
                                        ? rs.getDate("fecha_recepcion").toLocalDate()
                                        : null);
                        caso.setSintesis(rs.getString("sintesis"));
                        caso.setTramite(rs.getString("tramite"));
                        caso.setCantBeneficiarios(rs.getObject("cant_beneficiarios", Integer.class));
                        caso.setEstatus(rs.getString("estatus"));
                        caso.setCodCasoTribunal(rs.getString("cod_caso_tribunal"));
                        caso.setFechaResCasoTri(rs.getDate("fecha_res_caso_tri") != null
                                        ? rs.getDate("fecha_res_caso_tri").toLocalDate()
                                        : null);
                        caso.setFechaCreaCasoTri(rs.getDate("fecha_crea_caso_tri") != null
                                        ? rs.getDate("fecha_crea_caso_tri").toLocalDate()
                                        : null);
                        caso.setIdTribunal(rs.getObject("id_tribunal", Integer.class));
                        caso.setTermino(rs.getString("termino"));
                        caso.setIdCentro(rs.getObject("id_centro", Integer.class));
                        caso.setCedula(rs.getString("cedula"));
                        caso.setUsername(rs.getString("username"));
                        caso.setComAmbLegal(rs.getObject("com_amb_legal", Integer.class));
                        return caso;
                }
        };

        public List<Caso> findAll() {
                String sql = "SELECT * FROM casos";
                return jdbcTemplate.query(sql, rowMapper);
        }

        public Optional<Caso> findById(String numCaso) {
                String sql = "SELECT * FROM casos WHERE num_caso = ?";
                List<Caso> result = jdbcTemplate.query(sql, rowMapper, numCaso);
                return result.stream().findFirst();
        }

        public int save(Caso c) {
                String sql = "INSERT INTO casos (num_caso, fecha_recepcion, sintesis, tramite, cant_beneficiarios, estatus, "
                                +
                                "cod_caso_tribunal, fecha_res_caso_tri, fecha_crea_caso_tri, id_tribunal, termino, id_centro, "
                                +
                                "cedula, username, com_amb_legal) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                return jdbcTemplate.update(sql,
                                c.getNumCaso(), c.getFechaRecepcion(), c.getSintesis(), c.getTramite(),
                                c.getCantBeneficiarios(), c.getEstatus(),
                                c.getCodCasoTribunal(), c.getFechaResCasoTri(), c.getFechaCreaCasoTri(),
                                c.getIdTribunal(), c.getTermino(), c.getIdCentro(),
                                c.getCedula(), c.getUsername(), c.getComAmbLegal());
        }

        public int update(Caso c) {
                String sql = "UPDATE casos SET fecha_recepcion = ?, sintesis = ?, tramite = ?, cant_beneficiarios = ?, estatus = ?, "
                                +
                                "cod_caso_tribunal = ?, fecha_res_caso_tri = ?, fecha_crea_caso_tri = ?, id_tribunal = ?, termino = ?, "
                                +
                                "id_centro = ?, cedula = ?, username = ?, com_amb_legal = ? WHERE num_caso = ?";
                return jdbcTemplate.update(sql,
                                c.getFechaRecepcion(), c.getSintesis(), c.getTramite(), c.getCantBeneficiarios(),
                                c.getEstatus(),
                                c.getCodCasoTribunal(), c.getFechaResCasoTri(), c.getFechaCreaCasoTri(),
                                c.getIdTribunal(), c.getTermino(),
                                c.getIdCentro(), c.getCedula(), c.getUsername(), c.getComAmbLegal(), c.getNumCaso());
        }

        public int delete(String numCaso) {
                String sql = "DELETE FROM casos WHERE num_caso = ?";
                return jdbcTemplate.update(sql, numCaso);
        }
}
