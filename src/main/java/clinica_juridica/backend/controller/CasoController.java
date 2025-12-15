package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.request.CasoCreateRequest;
import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.service.CasoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/casos")
public class CasoController {

    private final CasoService casoService;

    public CasoController(CasoService casoService) {
        this.casoService = casoService;
    }

    @GetMapping("/list")        
    public ResponseEntity<List<CasoListResponse>> getAllCasos() {
        List<CasoListResponse> casos = casoService.findAllWithSolicitanteInfo();
        return ResponseEntity.ok(casos);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCaso(@RequestBody CasoCreateRequest request) {
        Caso caso = new Caso();
        caso.setFechaRecepcion(request.fechaInicio());
        caso.setEstatus(request.estado());
        caso.setSintesis(request.descripcion());
        caso.setIdSolicitante(request.idSolicitante());
        caso.setIdAmbitoLegal(request.idAmbitoLegal());

        String result = casoService.createCaso(caso);
        return ResponseEntity.ok(result);
    }
}
