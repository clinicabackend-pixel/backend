package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.request.AuthLoginRequest;
import clinica_juridica.backend.dto.response.AuthLoginResponse;
import clinica_juridica.backend.dto.response.UsuarioResponse; // Added import
import clinica_juridica.backend.models.Usuario; // Added import
import clinica_juridica.backend.repository.UsuarioRepository; // Added import
import clinica_juridica.backend.security.JwtUtil;
import clinica_juridica.backend.security.CustomUserDetailsService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder; // Added import
import org.springframework.web.bind.annotation.GetMapping; // Added import
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation; // Added import
import io.swagger.v3.oas.annotations.tags.Tag; // Added import

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para login y validación de sesión")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final clinica_juridica.backend.service.UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            UsuarioRepository usuarioRepository,
            clinica_juridica.backend.service.UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/setup-password")
    @Operation(summary = "Establecer contraseña", description = "Permite establecer la contraseña usando un token de invitación válido.")
    public ResponseEntity<String> setupPassword(
            @RequestBody clinica_juridica.backend.dto.request.SetupPasswordRequest request) {
        try {
            usuarioService.setupPassword(request.getToken(), request.getContrasena());
            return ResponseEntity.ok("Contraseña establecida exitosamente. Ahora puedes iniciar sesión.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida credenciales y devuelve un token JWT. Requiere que el usuario esté ACTIVO.")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthLoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthLoginResponse(jwt));
    }

    @GetMapping("/me")
    @Operation(summary = "Obtener usuario actual", description = "Devuelve la información del usuario autenticado basado en el token JWT.")
    public ResponseEntity<UsuarioResponse> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String termino = usuarioService.getTerminoByUsername(usuario.getUsername(), usuario.getTipo());

        return ResponseEntity.ok(new UsuarioResponse(
                usuario.getCedula(),
                usuario.getNombre(),
                "N/A", // Sexo not in Usuario model
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getStatus(),
                usuario.getTipo(),
                termino));
    }

    @GetMapping("/activo")
    @Operation(summary = "Verificar estado del servicio", description = "Devuelve 'ACTIVO' si el servicio está funcionando.")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("ACTIVO");
    }
}
