package clinica_juridica.backend.service;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.stereotype.Service;
import clinica_juridica.backend.dto.response.CasoListaResponse;

@Service
public class CasoService {

    private final CasoRepository casoRepository;

    public CasoService(CasoRepository casoRepository) {
        this.casoRepository = casoRepository;
    }

    public Iterable<Caso> findAllCasos() {
        return casoRepository.findAll();
    }
}
