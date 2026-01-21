package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.response.ProfesorResponse;
import clinica_juridica.backend.repository.ProfesorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    public List<ProfesorResponse> getProfesores() {
        return profesorRepository.findAllProjected();
    }
}
