package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.service.UsuarioPerfilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioPerfilController {

    private final UsuarioPerfilService usuarioPerfilService;

    public UsuarioPerfilController(UsuarioPerfilService usuarioPerfilService) {
        this.usuarioPerfilService = usuarioPerfilService;
    }

    @GetMapping("/personal")
    public ResponseEntity<Iterable<UsuarioResponse>> getAllPersonal() {
        Iterable<Usuario> personal = usuarioPerfilService.findAllUsuarios();
        Iterable<UsuarioResponse> response = StreamSupport.stream(personal.spliterator(), false)
                .map(p -> new UsuarioResponse(p.getIdUsuario(), p.getNombre(), p.getSexo(), p.getEmail(),
                        p.getUsername(), p.getEstatus(), p.getTipoUsuario()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/solicitantes")
    public ResponseEntity<Iterable<SolicitanteResponse>> getAllSolicitantes() {
        Iterable<Solicitante> solicitantes = usuarioPerfilService.findAllSolicitantes();
        Iterable<SolicitanteResponse> response = StreamSupport.stream(solicitantes.spliterator(), false)
                .map(s -> new SolicitanteResponse(
                        s.getIdSolicitante(), s.getNombre(), s.getSexo(), s.getFNacimiento(),
                        s.getEstadoCivil(), s.getIdParroquia(),
                        s.getIdNivelEdu(), s.getIdVivienda(), s.getIdTrabajo()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
