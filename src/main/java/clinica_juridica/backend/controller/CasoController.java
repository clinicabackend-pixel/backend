package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.service.CasoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/casos")
public class CasoController {

    private final CasoService casoService;

    public CasoController(CasoService casoService) {
        this.casoService = casoService;
    }

    @GetMapping
    public ResponseEntity<Iterable<CasoResponse>> getAllCasos() {
        Iterable<Caso> casos = casoService.findAllCasos();
        Iterable<CasoResponse> response = StreamSupport.stream(casos.spliterator(), false)
                .map(c -> new CasoResponse(
                        c.getNumCaso(), c.getFechaInicio(), c.getEstado(),
                        c.getDescripcion(), c.getIdSolicitante(), c.getIdAmbitoLegal()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
