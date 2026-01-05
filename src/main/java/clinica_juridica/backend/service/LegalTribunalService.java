package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Tribunal;
import clinica_juridica.backend.repository.TribunalRepository;
import org.springframework.stereotype.Service;

@Service
public class LegalTribunalService {

    private final TribunalRepository tribunalRepository;

    public LegalTribunalService(TribunalRepository tribunalRepository) {
        this.tribunalRepository = tribunalRepository;
    }

    public Iterable<Tribunal> findAllTribunales() {
        return tribunalRepository.findAll();
    }
}
