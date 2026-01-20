package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.request.DocumentoCreateRequest;
import clinica_juridica.backend.dto.response.DocumentoResponse;
import clinica_juridica.backend.models.Documento;
import clinica_juridica.backend.repository.DocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    public List<DocumentoResponse> getDocumentosByCaso(String numCaso) {
        return documentoRepository.findAllByNumCaso(numCaso).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public DocumentoResponse createDocumento(DocumentoCreateRequest request, String username) {
        Integer nextId = documentoRepository.findMaxIdByNumCaso(request.getNumCaso()) + 1;

        LocalDate fechaRegistro = LocalDate.now();

        documentoRepository.saveManual(
                nextId,
                request.getNumCaso(),
                fechaRegistro,
                request.getFolioIni(),
                request.getFolioFin(),
                request.getTitulo(),
                request.getObservacion(),
                username);

        return new DocumentoResponse(
                nextId,
                request.getNumCaso(),
                fechaRegistro,
                request.getFolioIni(),
                request.getFolioFin(),
                request.getTitulo(),
                request.getObservacion(),
                username);
    }

    @Transactional
    public void deleteDocumento(String numCaso, Integer idDocumento) {
        documentoRepository.deleteByNumCasoAndIdDocumento(numCaso, idDocumento);
    }

    private DocumentoResponse mapToResponse(Documento doc) {
        return new DocumentoResponse(
                doc.getIdDocumento(),
                doc.getNumCaso(),
                doc.getFechaRegistro(),
                doc.getFolioIni(),
                doc.getFolioFin(),
                doc.getTitulo(),
                doc.getObservacion(),
                doc.getUsername());
    }
}
