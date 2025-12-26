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
import clinica_juridica.backend.dto.CasoDetalleDTO;
import clinica_juridica.backend.repository.AccionRepository;
import clinica_juridica.backend.repository.EncuentroRepository;
import clinica_juridica.backend.repository.DocumentoRepository;
import clinica_juridica.backend.repository.PruebaRepository;
import clinica_juridica.backend.dto.CasoAsignadoDTO;
import clinica_juridica.backend.dto.CasoSupervisadoDTO;
import clinica_juridica.backend.repository.CasoAsignadoRepository;
import clinica_juridica.backend.repository.CasoSupervisadoRepository;
import clinica_juridica.backend.repository.AccionEjecutadaRepository;
import clinica_juridica.backend.repository.EncuentroAtendidoRepository;

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

        public CasoService(CasoRepository casoRepository,
                        EstatusPorCasoRepository estatusPorCasoRepository,
                        AccionRepository accionRepository,
                        EncuentroRepository encuentroRepository,
                        DocumentoRepository documentoRepository,
                        PruebaRepository pruebaRepository,
                        CasoAsignadoRepository casoAsignadoRepository,
                        CasoSupervisadoRepository casoSupervisadoRepository,
                        AccionEjecutadaRepository accionEjecutadaRepository,
                        EncuentroAtendidoRepository encuentroAtendidoRepository) {
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
        }

        public List<Caso> getAll() {
                return (List<Caso>) casoRepository.findAll();
        }

        public List<Caso> getCasosByEstatus(String estatus) {
                return casoRepository.findAllByEstatus(estatus);
        }

        public List<Caso> getCasosAbiertosPorUsuario(String username) {
                return casoRepository.findAllByUsernameAndEstatus(username, "ABIERTO");
        }

        public Optional<Caso> getById(String id) {
                return casoRepository.findById(id);
        }

        public Caso create(Caso caso) {
                String numCaso = casoRepository.registrarNuevoCaso(
                                caso.getSintesis(),
                                caso.getTramite(),
                                caso.getCantBeneficiarios(),
                                caso.getIdTribunal(),
                                caso.getTermino(),
                                caso.getIdCentro(),
                                caso.getCedula(),
                                caso.getUsername(),
                                caso.getComAmbLegal());
                caso.setNumCaso(numCaso);
                return caso;
        }

        @Transactional
        public void update(String id, clinica_juridica.backend.dto.UpdateCasoDTO dto) {
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

                // Calcular siguiente correlativo
                Integer maxId = estatusPorCasoRepository.findMaxIdByNumCaso(id);
                Integer nextId = (maxId == null) ? 1 : maxId + 1;

                // Guardar historial manualmente
                estatusPorCasoRepository.saveNewStatus(nextId, id, DateUtils.getCurrentDate(), nuevoEstatus);

                // Actualizar estatus del caso
                casoRepository.updateEstatus(id, nuevoEstatus);
        }

        @Transactional
        public void createAccion(String numCaso, clinica_juridica.backend.dto.CreateAccionDTO dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                Integer maxId = accionRepository.findMaxIdByNumCaso(numCaso);
                Integer nextId = maxId + 1;
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
        public void createEncuentro(String numCaso, clinica_juridica.backend.dto.CreateEncuentroDTO dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                Integer maxId = encuentroRepository.findMaxIdByNumCaso(numCaso);
                Integer nextId = maxId + 1;
                encuentroRepository.saveManual(nextId, numCaso, dto.getFechaAtencion(), dto.getFechaProxima(),
                                dto.getOrientacion(), dto.getObservacion(), dto.getUsername());

                if (dto.getAtendidos() != null) {
                        for (String atendido : dto.getAtendidos()) {
                                encuentroAtendidoRepository.saveManual(nextId, numCaso, atendido);
                        }
                }
        }

        @Transactional
        public void createDocumento(String numCaso, clinica_juridica.backend.dto.CreateDocumentoDTO dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                Integer maxId = documentoRepository.findMaxIdByNumCaso(numCaso);
                Integer nextId = maxId + 1;
                documentoRepository.saveManual(nextId, numCaso, dto.getFechaRegistro(), dto.getFolioIni(),
                                dto.getFolioFin(),
                                dto.getTitulo(), dto.getObservacion(), dto.getUsername());
        }

        @Transactional
        public void createPrueba(String numCaso, clinica_juridica.backend.dto.CreatePruebaDTO dto) {
                if (!casoRepository.existsById(numCaso)) {
                        throw new RuntimeException("Caso no encontrado: " + numCaso);
                }
                Integer maxId = pruebaRepository.findMaxIdByNumCaso(numCaso);
                Integer nextId = maxId + 1;
                pruebaRepository.saveManual(nextId, numCaso, dto.getFecha(), dto.getDocumento(), dto.getObservacion(),
                                dto.getTitulo());
        }

        public List<Accion> getAcciones(String id) {
                return accionRepository.findAllByNumCaso(id);
        }

        public List<Encuentro> getEncuentros(String id) {
                return encuentroRepository.findAllByNumCaso(id);
        }

        public List<Documento> getDocumentos(String id) {
                return documentoRepository.findAllByNumCaso(id);
        }

        public List<Prueba> getPruebas(String id) {
                return pruebaRepository.findAllByNumCaso(id);
        }

        public List<CasoAsignadoDTO> getAsignados(String id) {
                return casoAsignadoRepository.findAllByNumCaso(id);
        }

        public List<CasoSupervisadoDTO> getSupervisores(String id) {
                return casoSupervisadoRepository.findAllByNumCaso(id);
        }

        public CasoDetalleDTO getCasoDetalle(String id) {
                Caso caso = casoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Caso no encontrado: " + id));
                return new CasoDetalleDTO(
                                caso,
                                getAcciones(id),
                                getEncuentros(id),
                                getDocumentos(id),
                                getPruebas(id),
                                getAsignados(id),
                                getSupervisores(id));
        }
}
