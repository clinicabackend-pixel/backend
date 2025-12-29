package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.request.UsuarioRequest;
import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.service.UsuarioService;
import java.util.stream.StreamSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para la gestión de usuarios (CRUD). Estandarizado para Coordinadores.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasRole('COORDINADOR') or hasRole('PROFESOR')")
    @Operation(summary = "Obtener lista de usuarios", description = "Devuelve una lista de todos los usuarios. Requiere rol COORDINADOR o PROFESOR.")
    public ResponseEntity<Iterable<UsuarioResponse>> getAllUsuarios() {
        Iterable<Usuario> personal = usuarioService.findAllUsuarios();
        Iterable<UsuarioResponse> response = StreamSupport.stream(personal.spliterator(), false)
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activos")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtener usuarios activos", description = "Devuelve una lista de todos los usuarios activos. Accesible para todos los roles authenticatedos.")
    public ResponseEntity<Iterable<UsuarioResponse>> getActiveUsuarios() {
        Iterable<Usuario> personal = usuarioService.findAllActiveUsuarios();
        Iterable<UsuarioResponse> response = StreamSupport.stream(personal.spliterator(), false)
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('COORDINADOR') or #username == authentication.name")
    @Operation(summary = "Obtener detalle de usuario", description = "Obtiene los detalles de un usuario específico. Un usuario puede ver su propio perfil; los Coordinadores pueden ver cualquiera.")
    public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable String username) {
        Usuario usuario = usuarioService.findUsuarioByUsername(username);
        return ResponseEntity.ok(mapToResponse(usuario));
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINADOR')")
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario (Estudiante, Profesor o Coordinador). Requiere rol COORDINADOR.")
    public ResponseEntity<String> createUsuario(@RequestBody UsuarioRequest request) {
        usuarioService.createUsuario(request);
        return ResponseEntity.ok("Usuario creado exitosamente");
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('COORDINADOR')")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente. Requiere rol COORDINADOR.")
    public ResponseEntity<String> updateUsuario(@PathVariable String username, @RequestBody UsuarioRequest request) {
        usuarioService.updateUsuario(username, request);
        return ResponseEntity.ok("Usuario actualizado exitosamente");
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('COORDINADOR')")
    @Operation(summary = "Desactivar usuario", description = "Cambia el estatus de un usuario a INACTIVO. Requiere rol COORDINADOR.")
    public ResponseEntity<String> deleteUsuario(@PathVariable String username) {
        usuarioService.deleteUsuario(username);
        return ResponseEntity.ok("Usuario desactivado exitosamente");
    }

    private UsuarioResponse mapToResponse(Usuario p) {
        return new UsuarioResponse(p.getCedula(), p.getNombre(), "N/A", p.getEmail(),
                p.getUsername(), p.getStatus(), p.getTipo());
    }
}
