package clinica_juridica.backend.config;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.models.enums.TipoUsuario;
import clinica_juridica.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
@org.springframework.context.annotation.Profile("!test")
public class DataSeeder {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            CoordinadorRepository coordinadorRepository,
            ProfesorRepository profesorRepository,
            EstudianteRepository estudianteRepository,
            SemestreRepository semestreRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {

            // 0. Semester (Requerido para Profesor y Estudiante)
            String termino = "2026-15";
            if (!semestreRepository.existsById(termino)) {
                Semestre sem = new Semestre(termino, "Semestre Test", LocalDate.now(), LocalDate.now().plusMonths(6));
                semestreRepository.save(sem);
                System.out.println("SEEDER: Semestre creado: " + termino);
            }

            // 1. Coordinador (admin)
            if (!usuarioRepository.existsById("admin")) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setCedula("V-12345678");
                admin.setNombre("Coordinador Admin");
                admin.setEmail("admin@clinica.com");
                admin.setContrasena(passwordEncoder.encode("123456"));
                admin.setTipo(TipoUsuario.COORDINADOR.name());
                admin.setStatus("ACTIVO");
                admin.setNew(true);

                usuarioRepository.save(admin);

                Coordinador coord = new Coordinador("admin");
                coordinadorRepository.save(coord);

                System.out.println("SEEDER: Coordinador 'admin' creado con password '123456'");
            }

            // 2. Profesor (profesor1)
            if (!usuarioRepository.existsById("profesor1")) {
                Usuario prof = new Usuario();
                prof.setUsername("profesor1");
                prof.setCedula("V-87654321");
                prof.setNombre("Profesor Test");
                prof.setEmail("profesor1@clinica.com");
                prof.setContrasena(passwordEncoder.encode("123456"));
                prof.setTipo(TipoUsuario.PROFESOR.name());
                prof.setStatus("ACTIVO");
                prof.setNew(true);

                usuarioRepository.save(prof);

                Profesor profesor = new Profesor("profesor1", termino);
                profesorRepository.save(profesor);

                System.out.println("SEEDER: Profesor 'profesor1' creado con password '123456'");
            }

            // 3. Estudiante (estudiante1)
            if (!usuarioRepository.existsById("estudiante1")) {
                Usuario est = new Usuario();
                est.setUsername("estudiante1");
                est.setCedula("V-11223344");
                est.setNombre("Estudiante Test");
                est.setEmail("estudiante1@clinica.com");
                est.setContrasena(passwordEncoder.encode("123456"));
                est.setTipo(TipoUsuario.ESTUDIANTE.name());
                est.setStatus("ACTIVO");
                est.setNew(true);

                usuarioRepository.save(est);

                // Estudiante model varies, checking fields
                Estudiante estudiante = new Estudiante();
                estudiante.setUsername("estudiante1");
                estudiante.setTermino(termino);
                estudiante.setTipoDeEstudiante("PREGRADO");
                estudiante.setNrc(12345);

                estudianteRepository.save(estudiante);

                System.out.println("SEEDER: Estudiante 'estudiante1' creado con password '123456'");
            }
        };
    }
}
