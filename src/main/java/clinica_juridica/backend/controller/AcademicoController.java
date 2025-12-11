package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.service.AcademicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/academico")
public class AcademicoController {

    private final AcademicoService academicoService;

    public AcademicoController(AcademicoService academicoService) {
        this.academicoService = academicoService;
    }

    @GetMapping("/materias")
    public ResponseEntity<Iterable<MateriaResponse>> getAllMaterias() {
        Iterable<Materia> materias = academicoService.findAllMaterias();
        Iterable<MateriaResponse> response = StreamSupport.stream(materias.spliterator(), false)
                .map(m -> new MateriaResponse(m.getNrc(), m.getNombreMateria()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
