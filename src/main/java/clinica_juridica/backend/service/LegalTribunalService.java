package clinica_juridica.backend.service;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class LegalTribunalService {

    private final TribunalRepository tribunalRepository;
    private final ExpedienteTribunalRepository expedienteTribunalRepository;
    private final DocumentoTribunalRepository documentoTribunalRepository;

    public LegalTribunalService(TribunalRepository tribunalRepository,
            ExpedienteTribunalRepository expedienteTribunalRepository,
            DocumentoTribunalRepository documentoTribunalRepository) {
        this.tribunalRepository = tribunalRepository;
        this.expedienteTribunalRepository = expedienteTribunalRepository;
        this.documentoTribunalRepository = documentoTribunalRepository;
    }

    public Iterable<Tribunal> findAllTribunales() {
        return tribunalRepository.findAll();
    }
}
