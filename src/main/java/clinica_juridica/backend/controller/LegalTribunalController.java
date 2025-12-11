package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.service.LegalTribunalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/legal")
public class LegalTribunalController {

    private final LegalTribunalService legalTribunalService;

    public LegalTribunalController(LegalTribunalService legalTribunalService) {
        this.legalTribunalService = legalTribunalService;
    }

    @GetMapping("/tribunales")
    public ResponseEntity<Iterable<TribunalResponse>> getAllTribunales() {
        Iterable<Tribunal> tribunales = legalTribunalService.findAllTribunales();
        Iterable<TribunalResponse> response = StreamSupport.stream(tribunales.spliterator(), false)
                .map(t -> new TribunalResponse(t.getIdTribunal(), t.getTipoTribunal(), t.getNombreTribunal()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
