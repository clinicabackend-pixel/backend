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
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para login y validación de sesión")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida credenciales y devuelve un token JWT. Requiere que el usuario esté ACTIVO.")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthLoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e) {
            throw new Exception("Usuario o contraseña incorrectos", e);
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

        return ResponseEntity.ok(new UsuarioResponse(
                usuario.getCedula(),
                usuario.getNombre(),
                "N/A", // Sexo not in Usuario model
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getStatus(),
                usuario.getTipo()));
    }
}
