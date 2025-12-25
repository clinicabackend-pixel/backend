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

@Service
@SuppressWarnings("null")
public class CasoService {

        private final CasoRepository casoRepository;
        private final EstatusPorCasoRepository estatusPorCasoRepository;
        private final AccionRepository accionRepository;
        private final EncuentroRepository encuentroRepository;
        private final DocumentoRepository documentoRepository;
        private final PruebaRepository pruebaRepository;

        public CasoService(CasoRepository casoRepository,
                        EstatusPorCasoRepository estatusPorCasoRepository,
                        AccionRepository accionRepository,
                        EncuentroRepository encuentroRepository,
                        DocumentoRepository documentoRepository,
                        PruebaRepository pruebaRepository) {
                this.casoRepository = casoRepository;
                this.estatusPorCasoRepository = estatusPorCasoRepository;
                this.accionRepository = accionRepository;
                this.encuentroRepository = encuentroRepository;
                this.documentoRepository = documentoRepository;
                this.pruebaRepository = pruebaRepository;
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

        public Caso update(String id, Caso caso) {
                caso.setNumCaso(id);
                return casoRepository.save(caso);
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

        public CasoDetalleDTO getCasoDetalle(String id) {
                Caso caso = casoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Caso no encontrado: " + id));
                return new CasoDetalleDTO(
                                caso,
                                getAcciones(id),
                                getEncuentros(id),
                                getDocumentos(id),
                                getPruebas(id));
        }
}
