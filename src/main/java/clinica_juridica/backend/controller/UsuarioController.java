package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.request.UsuarioRequest;
import clinica_juridica.backend.dto.response.UsuarioResponse;
import clinica_juridica.backend.models.Usuario;
import clinica_juridica.backend.service.UsuarioService;

import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(summary = "Obtener lista de usuarios", description = "Devuelve una lista de usuarios, opcionalmente filtrada por estatus. Requiere rol COORDINADOR o PROFESOR.")
    public ResponseEntity<Iterable<UsuarioResponse>> getAllUsuarios(@RequestParam(required = false) String estatus) {
        Iterable<Usuario> personal = usuarioService.findAllUsuarios(estatus);
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
        System.out.println("Creando usuario: " + request.getUsername() + ", Termino: " + request.getTermino());
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

    @DeleteMapping("/{username}/permanent")
    @PreAuthorize("hasRole('COORDINADOR')")
    @Operation(summary = "Eliminar usuario permanentemente", description = "Elimina físicamente el usuario y sus registros asociados. IRREVERSIBLE. Requiere rol COORDINADOR.")
    public ResponseEntity<String> deleteUsuarioPermanently(@PathVariable String username) {
        usuarioService.deleteUsuarioPermanently(username);
        return ResponseEntity.ok("Usuario eliminado permanentemente");
    }

    private UsuarioResponse mapToResponse(Usuario p) {
        String termino = usuarioService.getTerminoByUsername(p.getUsername(), p.getTipo());
        return new UsuarioResponse(p.getCedula(), p.getNombre(), p.getSexo(), p.getEmail(),
                p.getUsername(), p.getStatus(), p.getTipo(), termino);
    }
}
