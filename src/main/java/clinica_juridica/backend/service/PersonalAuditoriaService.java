package clinica_juridica.backend.service;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class PersonalAuditoriaService {

    private final AccionRepository accionRepository;
    private final CambioEstatusRepository cambioEstatusRepository;
    private final CitaAtendidaRepository citaAtendidaRepository;

    public PersonalAuditoriaService(AccionRepository accionRepository,
            CambioEstatusRepository cambioEstatusRepository,
            CitaAtendidaRepository citaAtendidaRepository) {
        this.accionRepository = accionRepository;
        this.cambioEstatusRepository = cambioEstatusRepository;
        this.citaAtendidaRepository = citaAtendidaRepository;
    }

    public Iterable<Accion> findAllAcciones() {
        return accionRepository.findAll();
    }
}
