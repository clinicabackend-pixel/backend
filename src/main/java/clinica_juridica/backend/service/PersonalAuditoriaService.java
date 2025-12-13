package clinica_juridica.backend.service;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class PersonalAuditoriaService {

    private final AccionRepository accionRepository;

    public PersonalAuditoriaService(AccionRepository accionRepository) {
        this.accionRepository = accionRepository;
    }

    public Iterable<Accion> findAllAcciones() {
        return accionRepository.findAll();
    }
}
