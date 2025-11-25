package clinica_juridica.backend.infrastructure.web.controller;

import clinica_juridica.backend.application.usecase.ObtenerCasosPorSolicitante;
import clinica_juridica.backend.application.usecase.RegistrarCaso;
import clinica_juridica.backend.domain.entities.Caso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone los endpoints del módulo de Casos.
 * Capa de infraestructura que recibe peticiones HTTP y delega a los casos de uso.
 */
@RestController
@RequestMapping("/api/casos")
public class CasoController {
    
    private final RegistrarCaso registrarCaso;
    private final ObtenerCasosPorSolicitante obtenerCasosPorSolicitante;
    
    public CasoController(RegistrarCaso registrarCaso, 
                          ObtenerCasosPorSolicitante obtenerCasosPorSolicitante) {
        this.registrarCaso = registrarCaso;
        this.obtenerCasosPorSolicitante = obtenerCasosPorSolicitante;
    }
    
    /**
     * POST /api/casos
     * Endpoint para crear un nuevo caso.
     * 
     * @param request DTO con los datos del caso a crear
     * @return ResponseEntity con el caso creado y status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Caso> crearCaso(@RequestBody CrearCasoRequest request) {
        try {
            // Delegar al caso de uso
            Caso casoCreado = registrarCaso.ejecutar(
                    new RegistrarCaso.ComandoCrearCaso(
                            request.sintesis(),
                            request.idSolicitante(),
                            request.tramite(),
                            request.cantBeneficiarios(),
                            request.idCentro(),
                            request.idAmbitoLegal()
                    )
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(casoCreado);
            
        } catch (IllegalArgumentException ex) {
            // Validación fallida
            return ResponseEntity.badRequest().build();
            
        } catch (Exception ex) {
            // Error interno
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/casos/solicitante/{id}
     * Endpoint para obtener todos los casos de un solicitante.
     * 
     * @param idSolicitante El ID del solicitante
     * @return ResponseEntity con la lista de casos y status 200 OK
     */
    @GetMapping("/solicitante/{id}")
    public ResponseEntity<List<Caso>> obtenerCasosPorSolicitante(@PathVariable("id") String idSolicitante) {
        try {
            // Delegar al caso de uso
            List<Caso> casos = obtenerCasosPorSolicitante.ejecutar(idSolicitante);
            return ResponseEntity.ok(casos);
            
        } catch (IllegalArgumentException ex) {
            // Validación fallida
            return ResponseEntity.badRequest().build();
            
        } catch (Exception ex) {
            // Error interno
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * DTO (Data Transfer Object) para la petición de creación de caso.
     * Record de Java para inmutabilidad y menos boilerplate.
     */
    public record CrearCasoRequest(
            String sintesis,
            String idSolicitante,
            String tramite,
            Integer cantBeneficiarios,
            Integer idCentro,
            Integer idAmbitoLegal
    ) {}
}
