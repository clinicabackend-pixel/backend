package clinica_juridica.backend.controller;

import clinica_juridica.backend.dto.request.DocumentoCreateRequest;
import clinica_juridica.backend.dto.response.DocumentoResponse;
import clinica_juridica.backend.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@Tag(name = "Documentos", description = "Gestión de documentos de casos")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @GetMapping("/caso/{numCaso}")
    @Operation(summary = "Listar documentos de un caso")
    public ResponseEntity<List<DocumentoResponse>> getDocumentos(@PathVariable String numCaso) {
        return ResponseEntity.ok(documentoService.getDocumentosByCaso(numCaso));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo documento")
    public ResponseEntity<DocumentoResponse> createDocumento(
            @RequestBody DocumentoCreateRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentoService.createDocumento(request, username));
    }

    @DeleteMapping("/caso/{numCaso}/{idDocumento}")
    @Operation(summary = "Eliminar un documento")
    public ResponseEntity<Void> deleteDocumento(
            @PathVariable String numCaso,
            @PathVariable Integer idDocumento) {
        documentoService.deleteDocumento(numCaso, idDocumento);
        return ResponseEntity.noContent().build();
    }
}
