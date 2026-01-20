package clinica_juridica.backend.controller;

import clinica_juridica.backend.service.FileUploadService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final FileUploadService fileUploadService;

    public AssetController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAsset(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> result = fileUploadService.uploadUniversalFile(file);

            String url = (String) result.get("secure_url");
            String format = (String) result.get("format");
            String type = (String) result.get("resource_type");
            String publicId = (String) result.get("public_id");

            return ResponseEntity.ok(Map.of(
                    "url", url,
                    "format", format != null ? format : "original",
                    "type", type,
                    "public_id", publicId,
                    "message", "Carga exitosa"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error interno: " + e.getMessage()));
        }
    }
}
