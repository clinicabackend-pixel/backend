package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.response.CasoListResponse;
import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CasoService {

    private final CasoRepository casoRepository;

    public CasoService(CasoRepository casoRepository) {
        this.casoRepository = casoRepository;
    }

    public List<CasoListResponse> findAllWithSolicitanteInfo() {
        return casoRepository.findAllWithSolicitanteInfo();
    }

    public String createCaso(Caso caso) {
        return casoRepository.createCaso(caso);
    }

    

}
