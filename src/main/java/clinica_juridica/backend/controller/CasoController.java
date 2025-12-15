package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.request.BeneficiarioRequest;
import clinica_juridica.backend.dto.request.CasoCreateRequest;
import clinica_juridica.backend.dto.request.CasoUpdateRequest;
import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.service.CasoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update/{numCaso}")
    public ResponseEntity<String> updateCaso(@PathVariable String numCaso, @RequestBody CasoUpdateRequest request) {
        Caso caso = new Caso();
        caso.setNumCaso(numCaso);
        caso.setFechaRecepcion(request.fechaInicio());
        caso.setEstatus(request.estado());
        caso.setSintesis(request.descripcion());
        caso.setIdSolicitante(request.idSolicitante());
        caso.setIdAmbitoLegal(request.idAmbitoLegal());

        String result = casoService.updateCaso(caso);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{numCaso}/beneficiarios")
    public ResponseEntity<String> addBeneficiario(@PathVariable String numCaso,
            @RequestBody BeneficiarioRequest request) {
        String result = casoService.addBeneficiario(numCaso, request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{numCaso}/detalle")
    public ResponseEntity<clinica_juridica.backend.dto.response.CasoDetalleResponse> getInfoCompleta(
            @PathVariable String numCaso) {
        clinica_juridica.backend.dto.response.CasoDetalleResponse response = casoService.getCasoDetalle(numCaso);
        return ResponseEntity.ok(response);
    }
}
