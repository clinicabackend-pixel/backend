package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.service.UsuarioPerfilService;
import java.util.stream.StreamSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                .map(p -> new UsuarioResponse(p.getCedula(), p.getNombre(), "N/A", p.getEmail(),
                        p.getUsername(), p.getStatus(), p.getTipo()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/solicitantes")
    public ResponseEntity<Iterable<SolicitanteResponse>> getAllSolicitantes() {
        Iterable<Solicitante> solicitantes = usuarioPerfilService.findAllSolicitantes();
        Iterable<SolicitanteResponse> response = StreamSupport.stream(
                solicitantes.spliterator(),
                false)
                .map(s -> new SolicitanteResponse(
                        s.getCedula(),
                        s.getNombre(),
                        s.getSexo(),
                        s.getFNacimiento(),
                        s.getIdEstadoCivil() != null ? s.getIdEstadoCivil().toString() : null,
                        null,
                        s.getIdNivel(),
                        null,
                        s.getIdCondicion() != null ? s.getIdCondicion().toString() : null))
                .toList();
        return ResponseEntity.ok(response);
    }
}
