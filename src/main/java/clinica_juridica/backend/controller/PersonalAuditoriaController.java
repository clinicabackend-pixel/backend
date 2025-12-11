package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.service.PersonalAuditoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/auditoria")
public class PersonalAuditoriaController {

    private final PersonalAuditoriaService personalAuditoriaService;

    public PersonalAuditoriaController(PersonalAuditoriaService personalAuditoriaService) {
        this.personalAuditoriaService = personalAuditoriaService;
    }

    @GetMapping("/acciones")
    public ResponseEntity<Iterable<AccionResponse>> getAllAcciones() {
        Iterable<Accion> acciones = personalAuditoriaService.findAllAcciones();
        Iterable<AccionResponse> response = StreamSupport.stream(acciones.spliterator(), false)
                .map(a -> new AccionResponse(a.getIdAccion(), a.getFecha(), a.getDescripcion(), a.getTipoAccion()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
