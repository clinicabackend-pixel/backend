package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Estado;
import clinica_juridica.backend.models.Municipio;
import clinica_juridica.backend.models.Parroquia;
import clinica_juridica.backend.models.Solicitante;
import clinica_juridica.backend.repository.EstadoRepository;
import clinica_juridica.backend.repository.MunicipioRepository;
import clinica_juridica.backend.repository.ParroquiaRepository;
import clinica_juridica.backend.repository.SolicitanteRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.List;
import java.util.Optional;

import clinica_juridica.backend.dto.request.SolicitanteRequest;
import clinica_juridica.backend.dto.response.SolicitanteResponse;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service

public class SolicitanteService {

    private final SolicitanteRepository solicitanteRepository;
    private final ParroquiaRepository parroquiaRepository;
    private final MunicipioRepository municipioRepository;
    private final EstadoRepository estadoRepository;

    public SolicitanteService(SolicitanteRepository solicitanteRepository,
            ParroquiaRepository parroquiaRepository,
            MunicipioRepository municipioRepository,
            EstadoRepository estadoRepository) {
        this.solicitanteRepository = solicitanteRepository;
        this.parroquiaRepository = parroquiaRepository;
        this.municipioRepository = municipioRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<SolicitanteResponse> getAll() {
        return StreamSupport.stream(solicitanteRepository.findAll().spliterator(), false)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<SolicitanteResponse> getById(@NonNull String id) {
        return solicitanteRepository.findById(id).map(this::mapToResponse);
    }

    @Transactional
    public void create(@NonNull SolicitanteRequest request) {
        Solicitante s = mapToEntity(request);
        solicitanteRepository.insertSolicitante(
                s.getCedula(),
                s.getNombre(),
                s.getSexo(),
                s.getNacionalidad(),
                s.getEmail(),
                s.getConcubinato(),
                s.getTelfCasa(),
                s.getTelfCelular(),
                s.getFNacimiento(),
                s.getIdEstadoCivil(),
                s.getIdParroquia(),
                s.getIdCondicion(),
                s.getIdCondicionActividad());
    }

    @Transactional
    public boolean update(@NonNull String id, @NonNull SolicitanteRequest request) {
        if (!solicitanteRepository.existsById(id)) {
            return false;
        }
        Solicitante solicitante = mapToEntity(request);
        solicitante.setCedula(id);
        solicitanteRepository.save(solicitante);
        return true;
    }

    public boolean exists(@NonNull String id) {
        return solicitanteRepository.existsById(id);
    }

    @Transactional
    public boolean delete(@NonNull String id) {
        if (!solicitanteRepository.existsById(id)) {
            return false;
        }
        solicitanteRepository.deleteById(id);
        return true;
    }

    private SolicitanteResponse mapToResponse(Solicitante s) {
        String nombreParroquia = "";
        String nombreMunicipio = "";
        String nombreEstado = "";

        if (s.getIdParroquia() != null) {
            Optional<Parroquia> pOpt = parroquiaRepository
                    .findById(Objects.requireNonNull(s.getIdParroquia()));
            if (pOpt.isPresent()) {
                nombreParroquia = pOpt.get().getNombreParroquia();
                Integer idMuni = pOpt.get().getIdMunicipio();
                if (idMuni != null) {
                    Optional<Municipio> mOpt = municipioRepository.findById(idMuni);
                    if (mOpt.isPresent()) {
                        nombreMunicipio = mOpt.get().getNombreMunicipio();
                        Integer idEst = mOpt.get().getIdEstado();
                        if (idEst != null) {
                            estadoRepository.findById(idEst).ifPresent(estado -> {
                                // Variable capture workaround if needed, but here simple assignment won't work
                                // in lambda for local var.
                                // So we restructure.
                            });
                            // Better structure without lambda for local var assignment
                            Optional<Estado> eOpt = estadoRepository.findById(idEst);
                            if (eOpt.isPresent()) {
                                nombreEstado = eOpt.get().getNombreEstado();
                            }
                        }
                    }
                }
            }
        }

        return new SolicitanteResponse(
                s.getCedula(),
                s.getNombre(),
                s.getSexo(),
                "SOLTERO", // Placeholder: Need lookup for idEstadoCivil
                s.getFNacimiento(),
                "SI".equals(s.getConcubinato()),
                s.getNacionalidad(),
                false, // Placeholder: need logic from idCondicionActividad
                "FORMAL", // Placeholder
                s.getTelfCasa(),
                s.getTelfCelular(),
                s.getEmail(),
                nombreEstado,
                nombreMunicipio,
                nombreParroquia,
                "" // Placeholder
        );
    }

    private Solicitante mapToEntity(SolicitanteRequest r) {
        Solicitante s = new Solicitante();
        s.setCedula(r.cedula());
        s.setNombre(r.nombre());
        s.setNacionalidad(r.nacionalidad());
        s.setSexo(r.sexo());
        s.setEmail(r.email());
        s.setConcubinato(Boolean.TRUE.equals(r.concubinato()) ? "SI" : "NO");
        s.setTelfCelular(r.telfCelular());
        s.setTelfCasa(r.telfCasa());
        s.setFNacimiento(r.fechaNacimiento());
        s.setIdEstadoCivil(r.idEstadoCivil());
        s.setIdParroquia(r.idParroquia());
        s.setIdCondicion(r.idCondicion());
        s.setIdCondicionActividad(r.idCondicionActividad());
        return s;
    }
}
