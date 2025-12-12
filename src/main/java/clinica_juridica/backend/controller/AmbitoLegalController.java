package clinica_juridica.backend.controller;

import clinica_juridica.backend.models.AmbitoLegal;
import clinica_juridica.backend.service.AmbitoLegalService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ambitos")
public class AmbitoLegalController {

    private final AmbitoLegalService service;

    public AmbitoLegalController(AmbitoLegalService service) {
        this.service = service;
    }

    @GetMapping("/buscar")
    public List<AmbitoLegal> buscar(@RequestParam String materia) {
        return service.getByMateria(materia);
    }
}
