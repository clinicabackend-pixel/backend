package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.repository.CasoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import clinica_juridica.backend.repository.EstatusPorCasoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@SuppressWarnings("null")
public class CasoService {

        private final CasoRepository casoRepository;
        private final EstatusPorCasoRepository estatusPorCasoRepository;

        public CasoService(CasoRepository casoRepository, EstatusPorCasoRepository estatusPorCasoRepository) {
                this.casoRepository = casoRepository;
                this.estatusPorCasoRepository = estatusPorCasoRepository;
        }

        public List<Caso> getAll() {
                return (List<Caso>) casoRepository.findAll();
        }

        public List<Caso> getCasosByEstatus(String estatus) {
                return casoRepository.findAllByEstatus(estatus);
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
                estatusPorCasoRepository.saveNewStatus(nextId, id, LocalDate.now(), nuevoEstatus);

                // Actualizar estatus del caso
                casoRepository.updateEstatus(id, nuevoEstatus);
        }
}
