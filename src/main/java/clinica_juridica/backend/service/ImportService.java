package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Estudiante;
import clinica_juridica.backend.models.Usuario;
import clinica_juridica.backend.models.enums.TipoUsuario;
import clinica_juridica.backend.repository.EstudianteRepository;
import clinica_juridica.backend.repository.UsuarioRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ImportService {

    private final UsuarioRepository usuarioRepository;
    private final EstudianteRepository estudianteRepository;

    public ImportService(UsuarioRepository usuarioRepository, EstudianteRepository estudianteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Transactional
    public Map<String, Object> importEstudiantes(MultipartFile file) throws IOException {
        Map<String, Object> report = new HashMap<>();
        int created = 0;
        int updated = 0;
        int failed = 0;
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(reader,
                        CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true)
                                .setTrim(true).build())) {

            for (CSVRecord csvRecord : csvParser) {
                try {
                    String cedula = csvRecord.get("CEDULA");
                    String nombre = csvRecord.get("NOMBRE_ESTUDIANTE");
                    String email = csvRecord.get("ESTU_EMAIL_ADDRESS");
                    // Assuming headers: CEDULA, NOMBRE_ESTUDIANTE, ESTU_EMAIL_ADDRESS, UCAB_USER,
                    // YEAR_TERM, CRN, STATUS_DESC, MODALIDAD

                    if (email == null || email.isBlank() || cedula == null || cedula.isBlank()) {
                        failed++;
                        errors.add("Row " + csvRecord.getRecordNumber() + ": Missing Email or Cedula");
                        continue;
                    }

                    // Check if usuario exists by Email or Cedula
                    Optional<Usuario> existingUserByEmail = usuarioRepository.findByEmail(email);
                    Optional<Usuario> existingUserByCedula = usuarioRepository.findByCedula(cedula);

                    Usuario usuario;
                    boolean isNewUser = false;

                    if (existingUserByEmail.isPresent()) {
                        usuario = existingUserByEmail.get();
                        // Update basic info if needed? For now, we assume existing user is fine.
                    } else if (existingUserByCedula.isPresent()) {
                        usuario = existingUserByCedula.get();
                        // Possible email update?
                        usuario.setEmail(email);
                    } else {
                        // Create new user
                        isNewUser = true;
                        usuario = new Usuario();
                        usuario.setUsername(email.split("@")[0]); // generate username from email
                        // Check if username collision?
                        if (usuarioRepository.existsById(Objects.requireNonNull(usuario.getUsername()))) {
                            usuario.setUsername(usuario.getUsername() + "_" + new Random().nextInt(1000));
                        }

                        usuario.setCedula(cedula);
                        usuario.setNombre(nombre);
                        usuario.setEmail(email);
                        usuario.setContrasena(null); // No password
                        usuario.setStatus("ACTIVO"); // Default status
                        usuario.setTipo(TipoUsuario.ESTUDIANTE.name());
                    }

                    usuarioRepository.save(Objects.requireNonNull(usuario));

                    // Create/Update Estudiante
                    String termino = csvRecord.isMapped("YEAR_TERM") ? csvRecord.get("YEAR_TERM") : "UNKNOWN";
                    String nrcStr = csvRecord.isMapped("CRN") ? csvRecord.get("CRN") : "0";
                    Integer nrc = 0;
                    try {
                        nrc = Integer.parseInt(nrcStr);
                    } catch (NumberFormatException e) {
                        // ignore
                    }

                    String modalidad = csvRecord.isMapped("MODALIDAD") ? csvRecord.get("MODALIDAD") : "";
                    String courseStatus = csvRecord.isMapped("COURSE_STATUS_INSC") ? csvRecord.get("COURSE_STATUS_INSC")
                            : "";
                    String tipoEstudiante = modalidad + " - " + courseStatus;

                    // Check if Estudiante relation exists
                    // Composite key logic might be needed if Estudiante uses composite key
                    // (username + termino)
                    // Based on Estudiante.java: @Id private String username; private String
                    // termino;
                    // But Repository is CrudRepository<Estudiante, String>, suggesting simple ID or
                    // maybe Spring Data JDBC handling.
                    // The Estudiante Entity has specific fields. Let's create a new object or
                    // update.

                    // Since specific lookup by composite key isn't standard in the simple repo
                    // without custom query,
                    // and existsByUsernameAndTermino was seen in EstudianteRepository.

                    if (!estudianteRepository.existsByUsernameAndTermino(usuario.getUsername(), termino)) {
                        Estudiante estudiante = new Estudiante(usuario.getUsername(), termino, tipoEstudiante, nrc);
                        estudianteRepository.save(estudiante);
                    }

                    if (isNewUser)
                        created++;
                    else
                        updated++;

                } catch (Exception e) {
                    failed++;
                    errors.add("Row " + csvRecord.getRecordNumber() + ": " + e.getMessage());
                }
            }
        }

        report.put("created", created);
        report.put("updated", updated);
        report.put("failed", failed);
        report.put("errors", errors);

        return report;
    }
}
