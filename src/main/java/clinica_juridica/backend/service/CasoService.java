package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.repository.CasoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import clinica_juridica.backend.repository.EstatusPorCasoRepository;
import org.springframework.transaction.annotation.Transactional;
import clinica_juridica.backend.utils.DateUtils;
import clinica_juridica.backend.models.Accion;
import clinica_juridica.backend.models.Encuentro;
import clinica_juridica.backend.models.Documento;
import clinica_juridica.backend.models.Prueba;
import clinica_juridica.backend.dto.response.CasoDetalleResponse;
import clinica_juridica.backend.dto.response.CasoSummaryResponse;
import clinica_juridica.backend.repository.AccionRepository;
import clinica_juridica.backend.repository.EncuentroRepository;
import clinica_juridica.backend.repository.DocumentoRepository;
import clinica_juridica.backend.repository.PruebaRepository;
import clinica_juridica.backend.dto.projection.CasoAsignadoProjection;
import clinica_juridica.backend.dto.projection.CasoSupervisadoProjection;
import clinica_juridica.backend.repository.CasoAsignadoRepository;
import clinica_juridica.backend.repository.CasoSupervisadoRepository;
import clinica_juridica.backend.repository.AccionEjecutadaRepository;
import clinica_juridica.backend.repository.EncuentroAtendidoRepository;
import clinica_juridica.backend.dto.request.*;
import clinica_juridica.backend.dto.response.AccionResponse;
import clinica_juridica.backend.dto.response.DocumentoResponse;
import clinica_juridica.backend.dto.response.EncuentroResponse;
import clinica_juridica.backend.dto.response.PruebaResponse;

@Service
@SuppressWarnings("null")
public class CasoService {

        private final CasoRepository casoRepository;
        private final EstatusPorCasoRepository estatusPorCasoRepository;
        private final AccionRepository accionRepository;
        private final EncuentroRepository encuentroRepository;
        private final DocumentoRepository documentoRepository;
        private final PruebaRepository pruebaRepository;
        private final CasoAsignadoRepository casoAsignadoRepository;
        private final CasoSupervisadoRepository casoSupervisadoRepository;
        private final AccionEjecutadaRepository accionEjecutadaRepository;
        private final EncuentroAtendidoRepository encuentroAtendidoRepository;
        private final clinica_juridica.backend.repository.EstudianteRepository estudianteRepository;
        private final clinica_juridica.backend.repository.ProfesorRepository profesorRepository;

        public CasoService(CasoRepository casoRepository,
                        EstatusPorCasoRepository estatusPorCasoRepository,
                        AccionRepository accionRepository,
                        EncuentroRepository encuentroRepository,
                        DocumentoRepository documentoRepository,
                        PruebaRepository pruebaRepository,
                        CasoAsignadoRepository casoAsignadoRepository,
                        CasoSupervisadoRepository casoSupervisadoRepository,
                        AccionEjecutadaRepository accionEjecutadaRepository,
                        EncuentroAtendidoRepository encuentroAtendidoRepository,
                        clinica_juridica.backend.repository.EstudianteRepository estudianteRepository,
                        clinica_juridica.backend.repository.ProfesorRepository profesorRepository) {
                this.casoRepository = casoRepository;
                this.estatusPorCasoRepository = estatusPorCasoRepository;
                this.accionRepository = accionRepository;
                this.encuentroRepository = encuentroRepository;
                this.documentoRepository = documentoRepository;
                this.pruebaRepository = pruebaRepository;
                this.casoAsignadoRepository = casoAsignadoRepository;
                this.casoSupervisadoRepository = casoSupervisadoRepository;
                this.accionEjecutadaRepository = accionEjecutadaRepository;
                this.encuentroAtendidoRepository = encuentroAtendidoRepository;
                this.estudianteRepository = estudianteRepository;
                this.profesorRepository = profesorRepository;
        }

        public List<CasoSummaryResponse> getAllSummary() {
                return casoRepository.findAllByFilters(null, null, null);
        }

        public List<CasoSummaryResponse> getCasosSummaryByEstatus(String estatus) {
                return casoRepository.findAllByFilters(estatus, null, null);
        }

        public List<CasoSummaryResponse> getCasosSummaryAbiertosPorUsuario(String username) {
                return casoRepository.findAllByFilters("ABIERTO", username, null);
        }

        public List<CasoSummaryResponse> getCasosSummaryByFilters(String estatus, String username, String termino) {
                return casoRepository.findAllByFilters(estatus, username, termino);
        }

        // Deprecated methods removed or updated? Keeping internal use methods if
        // needed, but strictly for Controller usage we want DTOs.
        // getById for Controller needs to return Caso? Controller uses it for 404
        // check.
        // Ideally Service has `exists(id)`.
        // I will keep getById returning Optional<Caso> for internal logic but adding
        // getDetail for Controller.

        public Optional<Caso> getById(String id) {
                return casoRepository.findById(id);
        }

        public Caso create(CasoCreateRequest request) {
                // Mapping Request to Entity (Manual mapping or Builder)
                // Note: numCaso is generated by DB function/procedure generally, but here
                // registrarnuevoCaso returns it.
                // We pass parameters.
                String numCaso = casoRepository.registrarNuevoCaso(
                                request.getSintesis(),
                                request.getTramite(),
                                request.getCantBeneficiarios(),
                                request.getIdTribunal(),
                                request.getTermino(),
                                request.getIdCentro(),
                                request.getCedula(),
                                request.getUsername(),
                                request.getComAmbLegal());

                // Fetch to return full object? Or just construct minimal?
                // Returning the created Caso entity is fine for now, or void. Controller
                // returns String currently.
                Caso caso = new Caso();
                caso.setNumCaso(numCaso);
                return caso;
        }

        @Transactional
        public void update(String id, CasoUpdateRequest dto) {
                if (!casoRepository.existsById(id)) {
                        throw new RuntimeException("Caso no encontrado: " + id);
                }
                casoRepository.updateManual(id, dto.getSintesis(),
                                dto.getCodCasoTribunal(), dto.getFechaResCasoTri(), dto.getFechaCreaCasoTri(),
                                dto.getIdTribunal(), dto.getComAmbLegal());
        }

        public void delete(String id) {
                casoRepository.deleteById(id);
        }

        @Transactional
        public void updateEstatus(String id, String nuevoEstatus) {
                if (!casoRepository.existsById(id)) {
                        throw new RuntimeException("Caso no encontrado: " + id);
                }

                Integer maxId = estatusPorCasoRepository.findMaxIdByNumCaso(id);
                Integer nextId = (maxId == null) ? 1 : maxId + 1;

                estatusPorCasoRepository.saveNewStatus(nextId, id, DateUtils.getCurrentDate(), nuevoEstatus);
                casoRepository.updateEstatus(id, nuevoEstatus);
        }

        // Sub-resources - Return DTOs

        public List<AccionResponse> getAcciones(String id) {
                return accionRepository.findAllByNumCaso(id).stream()
                                .map(this::mapToAccionResponse)
                                .toList();
        }

        private AccionResponse mapToAccionResponse(Accion accion) {
                return new AccionResponse(
                                accion.getIdAccion(),
                                accion.getNumCaso(),
                                accion.getTitulo(),
                                accion.getDescripcion(),
                                accion.getFechaRegistro(),
                                accion.getFechaEjecucion(),
                                accion.getUsername());
        }

        public List<EncuentroResponse> getEncuentros(String id) {
                return encuentroRepository.findAllByNumCaso(id).stream()
                                .map(this::mapToEncuentroResponse)
                                .toList();
        }

        private EncuentroResponse mapToEncuentroResponse(Encuentro encuentro) {
                return new EncuentroResponse(
                                encuentro.getIdEncuentros(),
                                encuentro.getNumCaso(),
                                encuentro.getFechaAtencion(),
                                encuentro.getFechaProxima(),
                                encuentro.getOrientacion(),
                                encuentro.getObservacion(),
                                encuentro.getUsername());
        }

        public List<DocumentoResponse> getDocumentos(String id) {
                return documentoRepository.findAllByNumCaso(id).stream()
                                .map(this::mapToDocumentoResponse)
                                .toList();
        }

        private DocumentoResponse mapToDocumentoResponse(Documento doc) {
                return new DocumentoResponse(
                                doc.getIdDocumento(),
                                doc.getNumCaso(),
                                doc.getFechaRegistro(),
                                doc.getFolioIni(),
                                doc.getFolioFin(),
                                doc.getTitulo(),
                                doc.getObservacion(),
                                doc.getUsername());
        }

        public List<PruebaResponse> getPruebas(String id) {
                return pruebaRepository.findAllByNumCaso(id).stream()
                                .map(this::mapToPruebaResponse)
                                .toList();
        }

        private PruebaResponse mapToPruebaResponse(Prueba prueba) {
                return new PruebaResponse(
                                prueba.getIdPrueba(),
                                prueba.getNumCaso(),
                                prueba.getFecha(),
                                prueba.getDocumento(),
                                prueba.getObservacion(),
                                prueba.getTitulo());
        }

        @Transactional
        public void createAccion(String numCaso, AccionCreateRequest dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                Integer maxId = accionRepository.findMaxIdByNumCaso(numCaso);
                Integer nextId = (maxId == null) ? 1 : maxId + 1;
                accionRepository.saveManual(nextId, numCaso, dto.getTitulo(), dto.getDescripcion(),
                                dto.getFechaRegistro(),
                                dto.getFechaEjecucion(), dto.getUsername());

                if (dto.getFechaEjecucion() != null && dto.getEjecutantes() != null) {
                        for (String ejecutante : dto.getEjecutantes()) {
                                accionEjecutadaRepository.saveManual(nextId, numCaso, ejecutante);
                        }
                }
        }

        @Transactional
        public void deleteAccion(String numCaso, Integer idAccion) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                accionRepository.deleteByNumCasoAndIdAccion(numCaso, idAccion);
        }

        @Transactional
        public void deleteEncuentro(String numCaso, Integer idEncuentro) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                encuentroRepository.deleteByNumCasoAndIdEncuentro(numCaso, idEncuentro);
        }

        @Transactional
        public void createEncuentro(String numCaso, EncuentroCreateRequest dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                Integer maxId = encuentroRepository.findMaxIdByNumCaso(numCaso);
                Integer nextId = (maxId == null) ? 1 : maxId + 1;
                encuentroRepository.saveManual(nextId, numCaso, dto.getFechaAtencion(), dto.getFechaProxima(),
                                dto.getOrientacion(), dto.getObservacion(), dto.getUsername());

                if (dto.getAtendidos() != null) {
                        for (String atendido : dto.getAtendidos()) {
                                encuentroAtendidoRepository.saveManual(nextId, numCaso, atendido);
                        }
                }
        }

        @Transactional
        public void createDocumento(String numCaso, DocumentoCreateRequest dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                Integer maxId = documentoRepository.findMaxIdByNumCaso(numCaso);
                Integer nextId = (maxId == null) ? 1 : maxId + 1;
                documentoRepository.saveManual(nextId, numCaso, dto.getFechaRegistro(), dto.getFolioIni(),
                                dto.getFolioFin(),
                                dto.getTitulo(), dto.getObservacion(), dto.getUsername());
        }

        @Transactional
        public void createPrueba(String numCaso, PruebaCreateRequest dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                Integer maxId = pruebaRepository.findMaxIdByNumCaso(numCaso);
                Integer nextId = (maxId == null) ? 1 : maxId + 1;
                pruebaRepository.saveManual(nextId, numCaso, dto.getFecha(), dto.getDocumento(), dto.getObservacion(),
                                dto.getTitulo());
        }

        @Transactional
        public void deleteDocumento(String numCaso, Integer idDocumento) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                documentoRepository.deleteByNumCasoAndIdDocumento(numCaso, idDocumento);
        }

        @Transactional
        public void deletePrueba(String numCaso, Integer idPrueba) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                pruebaRepository.deleteByNumCasoAndIdPrueba(numCaso, idPrueba);
        }

        @Transactional
        public void assignStudent(CasoAsignacionRequest dto) {
                if (!casoRepository.existsById(dto.getNumCaso())) {
                        throw new RuntimeException("Caso no encontrado: " + dto.getNumCaso());
                }

                // Validar que el estudiante exista en el término indicado
                if (!estudianteRepository.existsByUsernameAndTermino(dto.getUsername(), dto.getTermino())) {
                        throw new RuntimeException("El usuario '" + dto.getUsername()
                                        + "' no está registrado como estudiante en el término '" + dto.getTermino()
                                        + "'.");
                }

                casoAsignadoRepository.saveManual(dto.getNumCaso(), dto.getUsername(), dto.getTermino());
        }

        @Transactional
        public void assignSupervisor(CasoSupervisionRequest dto) {
                if (!casoRepository.existsById(dto.getNumCaso())) {
                        throw new RuntimeException("Caso no encontrado: " + dto.getNumCaso());
                }

                // Validar que el profesor existe en el término indicado
                if (!profesorRepository.existsByUsernameAndTermino(dto.getUsername(), dto.getTermino())) {
                        throw new RuntimeException("El usuario '" + dto.getUsername()
                                        + "' no está registrado como profesor en el término '" + dto.getTermino()
                                        + "'.");
                }

                casoSupervisadoRepository.saveManual(dto.getNumCaso(), dto.getUsername(), dto.getTermino());
        }

        @Transactional
        public void updateAccionFechaEjecucion(String numCaso, Integer idAccion, AccionExecutionDateRequest dto) {
                Caso caso = casoRepository.findById(numCaso)
                                .orElseThrow(() -> new RuntimeException("Caso no encontrado: " + numCaso));

                // Actualizar fecha
                accionRepository.updateFechaEjecucion(numCaso, idAccion, dto.getFechaEjecucion());

                // Actualizar lista de ejecutantes si se proporciona
                if (dto.getUsernames() != null) {
                        for (String username : dto.getUsernames()) {
                                // Validar que el estudiante esté registrado en el término del caso
                                if (!estudianteRepository.existsByUsernameAndTermino(username, caso.getTermino())) {
                                        throw new RuntimeException("El estudiante '" + username
                                                        + "' no está registrado en el término '" + caso.getTermino()
                                                        + "'.");
                                }

                                // Insertar solo si no existe previamente (evitar duplicados en el historial)
                                if (!accionEjecutadaRepository.existsByNumCasoAndIdAccionAndUsername(numCaso, idAccion,
                                                username)) {
                                        accionEjecutadaRepository.saveManual(idAccion, numCaso, username);
                                }
                        }
                }
        }

        @Transactional
        public CasoDetalleResponse getCasoDetalle(String id) {
                Caso caso = casoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Caso no encontrado: " + id));

                return new CasoDetalleResponse(
                                caso,
                                getAcciones(id),
                                getEncuentros(id),
                                getDocumentos(id),
                                getPruebas(id),
                                getAsignados(id),
                                getSupervisores(id));
        }

        public List<CasoAsignadoProjection> getAsignados(String id) {
                return casoAsignadoRepository.findAllByNumCaso(id);
        }

        public List<CasoSupervisadoProjection> getSupervisores(String id) {
                return casoSupervisadoRepository.findAllByNumCaso(id);
        }
}
