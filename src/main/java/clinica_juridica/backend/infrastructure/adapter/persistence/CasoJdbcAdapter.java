package clinica_juridica.backend.infrastructure.adapter.persistence;

import clinica_juridica.backend.application.port.output.CasoRepository;
import clinica_juridica.backend.domain.models.Caso;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de Caso usando JdbcTemplate con SQL nativo.
 * Adaptador que conecta el puerto de salida con la infraestructura de persistencia.
 */
@Repository
public class CasoJdbcAdapter implements CasoRepository {

    private final JdbcTemplate jdbcTemplate;
    
    // SQL nativo para insertar un nuevo caso
    private static final String INSERT_SQL = """
            INSERT INTO "CASOS" ("num_caso", "fecha_recepción", "cant_beneficiarios", "tramite", 
                               "estatus", "sintesis", "id_centro", "id_ambito_legal", "id_solicitante")
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    
    // SQL nativo para buscar por número de caso
    private static final String SELECT_BY_NUM_CASO_SQL = """
            SELECT "num_caso", "fecha_recepción", "cant_beneficiarios", "tramite", 
                   "estatus", "sintesis", "id_centro", "id_ambito_legal", "id_solicitante"
            FROM "CASOS"
            WHERE "num_caso" = ?
            """;
    
    // SQL nativo para buscar por solicitante (con ORDER BY para mejor UX)
    private static final String SELECT_BY_SOLICITANTE_SQL = """
            SELECT "num_caso", "fecha_recepción", "cant_beneficiarios", "tramite", 
                   "estatus", "sintesis", "id_centro", "id_ambito_legal", "id_solicitante"
            FROM "CASOS"
            WHERE "id_solicitante" = ?
            ORDER BY "fecha_recepción" DESC
            """;
    
    // SQL nativo para obtener todos los casos
    private static final String SELECT_ALL_SQL = """
            SELECT "num_caso", "fecha_recepción", "cant_beneficiarios", "tramite", 
                   "estatus", "sintesis", "id_centro", "id_ambito_legal", "id_solicitante"
            FROM "CASOS"
            ORDER BY "fecha_recepción" DESC
            """;
    
    // RowMapper dedicado para mapear ResultSet a la entidad Caso
    private final RowMapper<Caso> casoRowMapper = new CasoRowMapper();

    public CasoJdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Caso guardar(Caso caso) {
        try {
            // Generar número de caso único (formato: CASO-YYYYMMDD-HHMMSS-XXX)
            String numCaso = generarNumCaso();
            caso.setNumCaso(numCaso);
            
            // Ejecutar INSERT con JdbcTemplate
            int filasAfectadas = jdbcTemplate.update(
                    INSERT_SQL,
                    caso.getNumCaso(),
                    caso.getFechaRecepcion(),
                    caso.getCantBeneficiarios(),
                    caso.getTramite(),
                    caso.getEstatus(),
                    caso.getSintesis(),
                    caso.getIdCentro(),
                    caso.getIdAmbitoLegal(),
                    caso.getIdSolicitante()
            );
            
            if (filasAfectadas == 0) {
                throw new IllegalStateException("No se pudo insertar el caso en la base de datos");
            }
            
            return caso;
            
        } catch (DataAccessException ex) {
            throw new IllegalStateException("Error al guardar el caso: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<Caso> buscarPorNumCaso(String numCaso) {
        try {
            // Ejecutar SELECT con JdbcTemplate y RowMapper
            Caso caso = jdbcTemplate.queryForObject(
                    SELECT_BY_NUM_CASO_SQL,
                    casoRowMapper,
                    numCaso
            );
            return Optional.ofNullable(caso);
            
        } catch (EmptyResultDataAccessException ex) {
            // No se encontró el caso
            return Optional.empty();
            
        } catch (DataAccessException ex) {
            throw new IllegalStateException("Error al buscar el caso: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<Caso> buscarPorSolicitante(String idSolicitante) {
        try {
            // Ejecutar SELECT con JdbcTemplate que retorna lista
            return jdbcTemplate.query(
                    SELECT_BY_SOLICITANTE_SQL,
                    casoRowMapper,
                    idSolicitante
            );
            
        } catch (DataAccessException ex) {
            throw new IllegalStateException(
                    "Error al consultar los casos del solicitante " + idSolicitante + ": " + ex.getMessage(), 
                    ex
            );
        }
    }

    @Override
    public List<Caso> buscarTodos() {
        try {
            // Ejecutar SELECT sin parámetros
            return jdbcTemplate.query(SELECT_ALL_SQL, casoRowMapper);
            
        } catch (DataAccessException ex) {
            throw new IllegalStateException("Error al consultar todos los casos: " + ex.getMessage(), ex);
        }
    }
    
    /**
     * Genera un número de caso único basado en timestamp.
     * Formato: CASO-YYYYMMDD-HHMMSS-millis
     */
    private String generarNumCaso() {
        return String.format("CASO-%tY%<tm%<td-%<tH%<tM%<tS-%<tL", System.currentTimeMillis());
    }

    /**
     * RowMapper interno que convierte un ResultSet en una entidad Caso.
     * Implementa la lógica de mapeo de columnas SQL a atributos del dominio.
     */
    private static class CasoRowMapper implements RowMapper<Caso> {
        @Override
        public Caso mapRow(ResultSet rs, int rowNum) throws SQLException {
            Caso caso = new Caso();
            
            // Mapear cada columna del ResultSet a los atributos de la entidad
            // Nota: PostgreSQL convierte los nombres de columnas entre comillas a minúsculas
            caso.setNumCaso(rs.getString("num_caso"));
            
            // Convertir java.sql.Date a LocalDate
            java.sql.Date sqlDate = rs.getDate("fecha_recepción");
            if (sqlDate != null) {
                caso.setFechaRecepcion(sqlDate.toLocalDate());
            }
            
            caso.setCantBeneficiarios(rs.getInt("cant_beneficiarios"));
            if (rs.wasNull()) {
                caso.setCantBeneficiarios(null);
            }
            
            caso.setTramite(rs.getString("tramite"));
            caso.setEstatus(rs.getString("estatus"));
            caso.setSintesis(rs.getString("sintesis"));
            
            caso.setIdCentro(rs.getInt("id_centro"));
            if (rs.wasNull()) {
                caso.setIdCentro(null);
            }
            
            caso.setIdAmbitoLegal(rs.getInt("id_ambito_legal"));
            if (rs.wasNull()) {
                caso.setIdAmbitoLegal(null);
            }
            
            caso.setIdSolicitante(rs.getString("id_solicitante"));
            
            return caso;
        }
    }
}

