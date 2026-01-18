package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Municipio;
import clinica_juridica.backend.models.Parroquia;
import clinica_juridica.backend.models.Solicitante;
import clinica_juridica.backend.repository.MunicipioRepository;
import clinica_juridica.backend.repository.ParroquiaRepository;
import clinica_juridica.backend.repository.SolicitanteRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import clinica_juridica.backend.exception.ResourceAlreadyExistsException;
import java.util.Objects;
import java.util.List;
import java.util.Optional;
import clinica_juridica.backend.models.Familia;
import clinica_juridica.backend.models.VistaReporteVivienda;
import clinica_juridica.backend.repository.FamiliaRepository;
import clinica_juridica.backend.repository.VistaReporteViviendaRepository;
import clinica_juridica.backend.dto.response.EncuestaResponse;
import clinica_juridica.backend.dto.response.FamiliaDto;
import clinica_juridica.backend.dto.response.ViviendaDto;

import clinica_juridica.backend.models.Estado;
import clinica_juridica.backend.repository.EstadoRepository;
import clinica_juridica.backend.dto.request.SolicitanteRequest;
import clinica_juridica.backend.dto.response.SolicitanteResponse;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SolicitanteService {

    private final SolicitanteRepository solicitanteRepository;
    private final ParroquiaRepository parroquiaRepository;
    private final MunicipioRepository municipioRepository;
    private final FamiliaRepository familiaRepository;
    private final VistaReporteViviendaRepository vistaReporteViviendaRepository;
    private final EstadoRepository estadoRepository;

    public SolicitanteService(SolicitanteRepository solicitanteRepository,
            ParroquiaRepository parroquiaRepository,
            MunicipioRepository municipioRepository,
            FamiliaRepository familiaRepository,
            VistaReporteViviendaRepository vistaReporteViviendaRepository,
            EstadoRepository estadoRepository) {
        this.solicitanteRepository = solicitanteRepository;
        this.parroquiaRepository = parroquiaRepository;
        this.municipioRepository = municipioRepository;
        this.familiaRepository = familiaRepository;
        this.vistaReporteViviendaRepository = vistaReporteViviendaRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<SolicitanteResponse> getAll(boolean activeCasesOnly, String role) {
        Iterable<Solicitante> solicitantesIterable;

        if ("SOLICITANTE".equalsIgnoreCase(role)) {
            solicitantesIterable = activeCasesOnly
                    ? solicitanteRepository.findSolicitantesConCasosActivos()
                    : solicitanteRepository.findSolicitantesTitulares();
        } else if ("BENEFICIARIO".equalsIgnoreCase(role)) {
            solicitantesIterable = activeCasesOnly
                    ? solicitanteRepository.findBeneficiariosActivos()
                    : solicitanteRepository.findBeneficiarios();
        } else {
            solicitantesIterable = activeCasesOnly
                    ? solicitanteRepository.findParticipantesActivos()
                    : solicitanteRepository.findAll();
        }

        List<Solicitante> solicitantes = StreamSupport.stream(solicitantesIterable.spliterator(), false)
                .collect(Collectors.toList());

        return mapToResponseBatch(solicitantes);
    }

    public List<SolicitanteResponse> getAll() {
        return getAll(false, "TODOS");
    }

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SolicitanteService.class);

    public Optional<EncuestaResponse> getEncuesta(@NonNull String cedula) {
        Familia familia = null;
        try {
            familia = familiaRepository.findById(cedula).orElse(null);
        } catch (Exception e) {
            logger.error("Error fetching Familia for cedula: " + cedula, e);
        }

        VistaReporteVivienda vivienda = null;
        try {
            vivienda = vistaReporteViviendaRepository.findById(cedula).orElse(null);
        } catch (Exception e) {
            logger.error("Error fetching VistaReporteVivienda for cedula: " + cedula, e);
        }

        if (familia == null && vivienda == null) {
            return Optional.empty();
        }

        FamiliaDto familiaDto = null;
        if (familia != null) {
            familiaDto = new FamiliaDto(
                    familia.getCedula(),
                    familia.getCantPersonas(),
                    familia.getIngresoMes(),
                    familia.getJefeFamilia(),
                    familia.getCantNinos(),
                    familia.getCantTrabaja());
        }

        ViviendaDto viviendaDto = null;
        if (vivienda != null) {
            viviendaDto = new ViviendaDto(
                    vivienda.getCedula(),
                    vivienda.getTipoVivienda(),
                    vivienda.getCantHabit(),
                    vivienda.getCantBanos(),
                    vivienda.getMaterialPiso(),
                    vivienda.getMaterialParedes(),
                    vivienda.getMaterialTecho(),
                    vivienda.getServicioAgua(),
                    vivienda.getEliminacionExcretas(),
                    vivienda.getAseoUrbano());
        }

        return Optional.of(new EncuestaResponse(familiaDto, viviendaDto));
    }

    public Optional<SolicitanteResponse> getById(@NonNull String id) {
        return solicitanteRepository.findById(id).map(s -> mapToResponseBatch(List.of(s)).get(0));
    }

    @Transactional
    public void create(@NonNull SolicitanteRequest request) {
        String cedula = Objects.requireNonNull(request.cedula(), "La cédula no puede ser nula");
        if (solicitanteRepository.existsById(cedula)) {
            Optional<Solicitante> existing = solicitanteRepository.findById(cedula);
            throw new ResourceAlreadyExistsException(
                    "Ya existe un solicitante con la cédula " + cedula,
                    existing.map(s -> mapToResponseBatch(List.of(s)).get(0)).orElse(null));
        }

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
        solicitante.setIsNew(false);
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

    @SuppressWarnings("null")
    private List<SolicitanteResponse> mapToResponseBatch(List<Solicitante> solicitantes) {
        if (solicitantes.isEmpty()) {
            return List.of();
        }

        // 1. Collect all Parroquia IDs
        List<Integer> parroquiaIds = solicitantes.stream()
                .map(Solicitante::getIdParroquia)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 2. Fetch Parroquias in batch
        java.util.Map<Integer, Parroquia> parroquiaMap = StreamSupport
                .stream(parroquiaRepository.findAllById(parroquiaIds).spliterator(), false)
                .collect(Collectors.toMap(Parroquia::getIdParroquia, java.util.function.Function.identity()));

        // 3. Collect Municipio IDs from fetched Parroquias
        List<Integer> municipioIds = parroquiaMap.values().stream()
                .map(Parroquia::getIdMunicipio)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 4. Fetch Municipios in batch
        // 4. Fetch Municipios in batch
        java.util.Map<Integer, Municipio> municipioMap = StreamSupport
                .stream(municipioRepository.findAllById(municipioIds).spliterator(), false)
                .collect(Collectors.toMap(Municipio::getIdMunicipio, java.util.function.Function.identity()));

        // 5. Map to response using in-memory maps
        List<Integer> estadoIds = municipioMap.values().stream()
                .map(Municipio::getIdEstado)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        java.util.Map<Integer, Estado> estadoMap = StreamSupport
                .stream(estadoRepository.findAllById(estadoIds).spliterator(), false)
                .collect(Collectors.toMap(Estado::getIdEstado,
                        java.util.function.Function.identity()));

        return solicitantes.stream().map(s -> {
            Integer idMuni = null;
            Integer idEst = null;
            String nombreParroquia = null;
            String nombreMunicipio = null;
            String nombreEstado = null;

            Integer idParroquia = s.getIdParroquia();
            if (idParroquia != null) {
                Parroquia p = parroquiaMap.get(idParroquia);
                if (p != null) {
                    nombreParroquia = p.getNombreParroquia();
                    idMuni = p.getIdMunicipio();
                    if (idMuni != null) {
                        Municipio m = municipioMap.get(idMuni);
                        if (m != null) {
                            nombreMunicipio = m.getNombreMunicipio();
                            idEst = m.getIdEstado();
                            if (idEst != null) {
                                Estado e = estadoMap.get(idEst);
                                if (e != null) {
                                    nombreEstado = e.getNombreEstado();
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
                    s.getIdEstadoCivil(),
                    s.getIdParroquia(),
                    idMuni,
                    idEst,
                    s.getIdCondicion(),
                    s.getIdCondicionActividad(),
                    s.getIdNivel(),
                    nombreParroquia,
                    nombreMunicipio,
                    nombreEstado);
        }).collect(Collectors.toList());
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
