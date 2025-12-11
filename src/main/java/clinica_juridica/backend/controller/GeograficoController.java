package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.response.*;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.service.GeograficoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/geografico")
public class GeograficoController {

    private final GeograficoService geograficoService;

    public GeograficoController(GeograficoService geograficoService) {
        this.geograficoService = geograficoService;
    }

    @GetMapping("/estados")
    public ResponseEntity<Iterable<EstadoResponse>> getAllEstados() {
        Iterable<Estado> estados = geograficoService.findAllEstados();
        // Manual mapping for now
        Iterable<EstadoResponse> response = StreamSupport.stream(estados.spliterator(), false)
                .map(e -> new EstadoResponse(e.getIdEstado(), e.getNombreEstado()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/municipios")
    public ResponseEntity<Iterable<MunicipioResponse>> getAllMunicipios() {
        Iterable<Municipio> municipios = geograficoService.findAllMunicipios();
        Iterable<MunicipioResponse> response = StreamSupport.stream(municipios.spliterator(), false)
                .map(m -> new MunicipioResponse(m.getIdMunicipio(), m.getNombreMunicipio(), m.getIdEstado()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/parroquias")
    public ResponseEntity<Iterable<ParroquiaResponse>> getAllParroquias() {
        Iterable<Parroquia> parroquias = geograficoService.findAllParroquias();
        Iterable<ParroquiaResponse> response = StreamSupport.stream(parroquias.spliterator(), false)
                .map(p -> new ParroquiaResponse(p.getIdParroquia(), p.getNombreParroquia(), p.getIdMunicipio()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/centros")
    public ResponseEntity<Iterable<CentroResponse>> getAllCentros() {
        Iterable<Centro> centros = geograficoService.findAllCentros();
        Iterable<CentroResponse> response = StreamSupport.stream(centros.spliterator(), false)
                .map(c -> new CentroResponse(c.getIdCentro(), c.getNombre(), c.getIdParroquia()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
