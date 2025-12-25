package clinica_juridica.backend.service;

import clinica_juridica.backend.models.Caso;
import clinica_juridica.backend.repository.CasoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("null")
public class CasoService {

        private final CasoRepository casoRepository;

        public CasoService(CasoRepository casoRepository) {
                this.casoRepository = casoRepository;
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
}
