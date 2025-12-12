package clinica_juridica.backend.service;

import clinica_juridica.backend.models.AmbitoLegal;
import clinica_juridica.backend.repository.AmbitoLegalRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AmbitoLegalService {

    private final AmbitoLegalRepository repository;

    public AmbitoLegalService(AmbitoLegalRepository repository) {
        this.repository = repository;
    }

    public List<AmbitoLegal> getByMateria(String materia) {
        return repository.buscarPorMateria(materia);
    }
}
