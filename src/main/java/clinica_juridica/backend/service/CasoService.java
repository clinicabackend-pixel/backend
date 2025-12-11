package clinica_juridica.backend.service;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class CasoService {

    private final AmbitoLegalRepository ambitoLegalRepository;
    private final CasoRepository casoRepository;
    private final BeneficiarioCasoRepository beneficiarioCasoRepository;
    private final CitaRepository citaRepository;
    private final PruebaRepository pruebaRepository;

    public CasoService(AmbitoLegalRepository ambitoLegalRepository,
            CasoRepository casoRepository,
            BeneficiarioCasoRepository beneficiarioCasoRepository,
            CitaRepository citaRepository,
            PruebaRepository pruebaRepository) {
        this.ambitoLegalRepository = ambitoLegalRepository;
        this.casoRepository = casoRepository;
        this.beneficiarioCasoRepository = beneficiarioCasoRepository;
        this.citaRepository = citaRepository;
        this.pruebaRepository = pruebaRepository;
    }

    public Iterable<Caso> findAllCasos() {
        return casoRepository.findAll();
    }
}
