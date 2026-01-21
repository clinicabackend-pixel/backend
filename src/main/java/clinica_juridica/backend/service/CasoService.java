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
import clinica_juridica.backend.dto.request.BeneficiarioUpdateRequest;

import clinica_juridica.backend.repository.SolicitanteRepository;
import clinica_juridica.backend.repository.BeneficiariosCasosRepository;
import clinica_juridica.backend.repository.EstudianteRepository;
import clinica_juridica.backend.repository.ProfesorRepository;
import clinica_juridica.backend.repository.TribunalRepository;
import clinica_juridica.backend.models.Semestre;
import clinica_juridica.backend.models.Centro;
import clinica_juridica.backend.repository.SemestreRepository;
import clinica_juridica.backend.repository.CentroRepository;

import clinica_juridica.backend.dto.response.BeneficiarioResponse;
import clinica_juridica.backend.dto.request.AccionUpdateRequest;
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
        private final EstudianteRepository estudianteRepository;
        private final ProfesorRepository profesorRepository;
        private final TribunalRepository tribunalRepository;
        private final BeneficiariosCasosRepository beneficiariosCasosRepository;
        private final SolicitanteRepository solicitanteRepository;
        private final SemestreRepository semestreRepository;
        private final CentroRepository centroRepository;

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
                        EstudianteRepository estudianteRepository,
                        ProfesorRepository profesorRepository,
                        TribunalRepository tribunalRepository,
                        BeneficiariosCasosRepository beneficiariosCasosRepository,
                        SolicitanteRepository solicitanteRepository,
                        SemestreRepository semestreRepository,
                        CentroRepository centroRepository) {
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
                this.tribunalRepository = tribunalRepository;
                this.beneficiariosCasosRepository = beneficiariosCasosRepository;
                this.solicitanteRepository = solicitanteRepository;
                this.semestreRepository = semestreRepository;
                this.centroRepository = centroRepository;
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

        public List<CasoSummaryResponse> getCasosTitular(String cedula) {
                return casoRepository.findCasosBySolicitanteCedula(cedula);
        }

        public List<CasoSummaryResponse> getCasosBeneficiario(String cedula) {
                return casoRepository.findCasosByBeneficiarioCedula(cedula);
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

        @Transactional
        public CasoDetalleResponse create(CasoCreateRequest request) {
                // 1. Obtener Término Activo
                Semestre terminoActivo = semestreRepository.findActiveSemester();
                if (terminoActivo == null) {
                        throw new RuntimeException("No se encontró un término académico activo para la fecha actual");
                }
                String termino = terminoActivo.getTermino();

                // 2. Obtener Abreviatura Centro
                Centro centro = centroRepository.findById(request.getIdCentro())
                                .orElseThrow(() -> new RuntimeException(
                                                "Centro no encontrado: " + request.getIdCentro()));
                String abreviatura = centro.getAbreviatura();
                if (abreviatura == null) {
                        throw new RuntimeException("El centro no tiene abreviatura configurada");
                }

                // 3. Calcular Correlativo usando Prefix
                String prefix = abreviatura + "-" + termino + "-";
                // Buscamos el MAX que empiece con ese prefijo (ignorando id_centro en la query)
                String maxNumCaso = casoRepository.findMaxNumCasoByPrefix(prefix);

                int nextCorrelativo = 1;
                if (maxNumCaso != null && maxNumCaso.startsWith(prefix)) {
                        try {
                                String numberPart = maxNumCaso.substring(prefix.length());
                                nextCorrelativo = Integer.parseInt(numberPart) + 1;
                        } catch (NumberFormatException e) {
                                // Si falla el parseo, asumimos 1 o logueamos warning
                                System.err.println("Error parseando correlativo: " + maxNumCaso);
                        }
                }

                String numCaso = prefix + String.format("%04d", nextCorrelativo);

                // 4. Crear Objeto Caso
                Caso caso = new Caso();
                caso.setNumCaso(numCaso);
                caso.setFechaRecepcion(DateUtils.getCurrentDate());
                caso.setSintesis(request.getSintesis());
                caso.setTramite(request.getTramite());
                Integer cantBeneficiarios = request.getCantBeneficiarios();
                if (cantBeneficiarios == null || cantBeneficiarios < 1) {
                        cantBeneficiarios = 1;
                }
                caso.setCantBeneficiarios(cantBeneficiarios);
                caso.setEstatus("ABIERTO");
                caso.setIdTribunal(request.getIdTribunal());
                caso.setTermino(termino);
                caso.setIdCentro(request.getIdCentro());
                caso.setCedula(request.getCedula());
                caso.setUsername(request.getUsername());
                caso.setComAmbLegal(request.getComAmbLegal());

                // Guardar Caso
                casoRepository.save(caso);

                // Registrar beneficiarios si existen
                if (request.getBeneficiarios() != null) {
                        for (BeneficiarioCreateRequest ben : request.getBeneficiarios()) {
                                beneficiariosCasosRepository.saveManual(
                                                ben.getCedula(),
                                                numCaso,
                                                ben.getTipoBeneficiario(),
                                                ben.getParentesco());
                        }
                }

                // Registrar encuentro inicial si hay orientación
                if (request.getOrientacion() != null && !request.getOrientacion().isEmpty()) {
                        Integer maxId = encuentroRepository.findMaxIdByNumCaso(numCaso);
                        Integer nextId = (maxId == null) ? 1 : maxId + 1;

                        encuentroRepository.saveManual(nextId, numCaso, DateUtils.getCurrentDate(),
                                        null, // Fecha proxima
                                        request.getOrientacion(),
                                        null, // Observacion
                                        request.getUsername());

                        if (request.getEstudiantesAtencion() != null) {
                                for (String studentUsername : request.getEstudiantesAtencion()) {
                                        encuentroAtendidoRepository.saveManual(nextId, numCaso, studentUsername);
                                }
                        }
                }

                return getCasoDetalle(numCaso);
        }

        @Transactional
        public void addBeneficiario(String numCaso, BeneficiarioCreateRequest ben) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                // Validar si ya existe (opcional pero recomendado)
                // Usamos saveManual que probablemente es un insert directo.
                // Si la PK compuesta (cedula, numCaso) existe, dará error SQL, que es aceptable
                // o podemos manejarlo.
                // Por ahora insertamos directo.
                beneficiariosCasosRepository.saveManual(
                                ben.getCedula(),
                                numCaso,
                                ben.getTipoBeneficiario(),
                                ben.getParentesco());
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

        @Transactional
        public void updateBeneficiario(String numCaso, String cedula, BeneficiarioUpdateRequest dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                // Validar si es necesario que el beneficiario exista en el caso,
                // pero el updateManual retorna 0 si no encuentra rows, así que es seguro.
                // Opcional: Validar existencia en tabla intermedia para lanzar excepción si no
                // existe.
                beneficiariosCasosRepository.updateManual(cedula, numCaso, dto.getTipoBeneficiario(),
                                dto.getParentesco());
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

                // estatusPorCasoRepository.saveNewStatus(nextId, id,
                // DateUtils.getCurrentDate(), nuevoEstatus);
                // NOTA: Se comenta porque el Trigger 'trg_auto_historial_estatus' ya registra
                // el cambio automáticamente.
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

                String observacionFinal = dto.getObservacion();
                if (dto.getUsername() != null && !dto.getUsername().isEmpty()) {
                        String userNote = " (Subido por: " + dto.getUsername() + ")";
                        if (observacionFinal == null) {
                                observacionFinal = userNote;
                        } else {
                                observacionFinal += userNote;
                        }
                }

                pruebaRepository.saveManual(nextId, numCaso, dto.getFecha(), dto.getDocumento(), observacionFinal,
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
        public void assignStudent(String numCaso, CasoAsignacionRequest dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }

                // Validar que el estudiante exista en el término indicado
                if (!estudianteRepository.existsByUsernameAndTermino(dto.getUsername(), dto.getTermino())) {
                        throw new RuntimeException("El usuario '" + dto.getUsername()
                                        + "' no está registrado como estudiante en el término '" + dto.getTermino()
                                        + "'.");
                }

                casoAsignadoRepository.saveManual(numCaso, dto.getUsername(), dto.getTermino());
        }

        @Transactional
        public void assignSupervisor(String numCaso, CasoSupervisionRequest dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }

                // Validar que el profesor existe en el término indicado
                if (!profesorRepository.existsByUsernameAndTermino(dto.getUsername(), dto.getTermino())) {
                        throw new RuntimeException("El usuario '" + dto.getUsername()
                                        + "' no está registrado como profesor en el término '" + dto.getTermino()
                                        + "'.");
                }

                // Validar si ya está asignado (Idempotencia)
                if (casoSupervisadoRepository.existsByNumCasoAndUsernameAndTermino(numCaso, dto.getUsername(),
                                dto.getTermino())) {
                        return; // Ya está asignado, retornamos éxito silenciosamente
                }

                casoSupervisadoRepository.saveManual(numCaso, dto.getUsername(), dto.getTermino());
        }

        @Transactional
        public void unassignStudent(String numCaso, String username, String termino) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                casoAsignadoRepository.deleteManual(numCaso, username, termino);
        }

        @Transactional
        public void unassignSupervisor(String numCaso, String username, String termino) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                casoSupervisadoRepository.deleteManual(numCaso, username, termino);
        }

        @Transactional
        public void updateAccion(String numCaso, Integer idAccion, AccionUpdateRequest dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }

                Accion accion = accionRepository.findByNumCasoAndIdAccion(numCaso, idAccion)
                                .orElseThrow(() -> new RuntimeException("Acción no encontrada: " + idAccion));

                // Actualizar campos permitidos (White List via DTO)
                if (dto.getTitulo() != null) {
                        accion.setTitulo(dto.getTitulo());
                }
                if (dto.getDescripcion() != null) {
                        accion.setDescripcion(dto.getDescripcion());
                }
                if (dto.getFechaEjecucion() != null) {
                        accion.setFechaEjecucion(dto.getFechaEjecucion());
                }

                accionRepository.save(accion);

                // Actualizar lista de ejecutantes si se proporciona
                if (dto.getUsernames() != null) {
                        // Nota: Aquí estamos asumiendo que se agregan nuevos ejecutantes.
                        // Si se quisiera reemplazar totalmente la lista, habría que borrar los
                        // anteriores primero.
                        // Basado en el código anterior, validamos y agregamos los que no existan.

                        // Necesitamos el "termino" del caso para validar estudiantes
                        Caso caso = casoRepository.findById(numCaso)
                                        .orElseThrow(() -> new RuntimeException("Caso no encontrado: " + numCaso));

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

                String nombreTribunal = null;
                if (caso.getIdTribunal() != null) {
                        nombreTribunal = tribunalRepository.findById(caso.getIdTribunal())
                                        .map(t -> t.getNombreTribunal())
                                        .orElse(null);
                }

                return new CasoDetalleResponse(
                                caso,
                                nombreTribunal,
                                getAcciones(id),
                                getEncuentros(id),
                                getDocumentos(id),
                                getPruebas(id),
                                getAsignados(id),
                                getSupervisores(id),
                                getBeneficiarios(id));
        }

        public List<BeneficiarioResponse> getBeneficiarios(String id) {
                return beneficiariosCasosRepository.findAllByNumCaso(id).stream()
                                .map(b -> {
                                        String nombre = solicitanteRepository.findById(b.getCedula())
                                                        .map(s -> s.getNombre())
                                                        .orElse("Desconocido");
                                        return new BeneficiarioResponse(
                                                        b.getCedula(),
                                                        b.getNumCaso(),
                                                        b.getTipoBeneficiario(),
                                                        b.getParentesco(),
                                                        nombre);
                                })
                                .toList();
        }

        public List<CasoAsignadoProjection> getAsignados(String id) {
                return casoAsignadoRepository.findAllByNumCaso(id);
        }

        public List<CasoSupervisadoProjection> getSupervisores(String id) {
                return casoSupervisadoRepository.findAllByNumCaso(id);
        }
}
