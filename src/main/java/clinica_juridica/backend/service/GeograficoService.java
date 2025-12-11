package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Centro;
import clinica_juridica.backend.models.Estado;
import clinica_juridica.backend.models.Municipio;
import clinica_juridica.backend.models.Parroquia;
import clinica_juridica.backend.repository.CentroRepository;
import clinica_juridica.backend.repository.EstadoRepository;
import clinica_juridica.backend.repository.MunicipioRepository;
import clinica_juridica.backend.repository.ParroquiaRepository;
import org.springframework.stereotype.Service;

@Service
public class GeograficoService {

    private final EstadoRepository estadoRepository;
    private final MunicipioRepository municipioRepository;
    private final ParroquiaRepository parroquiaRepository;
    private final CentroRepository centroRepository;

    public GeograficoService(EstadoRepository estadoRepository, MunicipioRepository municipioRepository,
            ParroquiaRepository parroquiaRepository, CentroRepository centroRepository) {
        this.estadoRepository = estadoRepository;
        this.municipioRepository = municipioRepository;
        this.parroquiaRepository = parroquiaRepository;
        this.centroRepository = centroRepository;
    }

    public Iterable<Estado> findAllEstados() {
        return estadoRepository.findAll();
    }

    public Iterable<Municipio> findAllMunicipios() {
        return municipioRepository.findAll();
    }

    public Iterable<Parroquia> findAllParroquias() {
        return parroquiaRepository.findAll();
    }

    public Iterable<Centro> findAllCentros() {
        return centroRepository.findAll();
    }
}
