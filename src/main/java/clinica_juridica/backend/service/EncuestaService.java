package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.request.CaracteristicaRequest;
import clinica_juridica.backend.dto.request.DatosEncuestaRequest;
import clinica_juridica.backend.dto.response.DatosEncuestaResponse;
import clinica_juridica.backend.models.CaracteristicaVivienda;
import clinica_juridica.backend.models.Familia;
import clinica_juridica.backend.models.Vivienda;
import clinica_juridica.backend.repository.CaracteristicaViviendaRepository;
import clinica_juridica.backend.repository.FamiliaRepository;
import clinica_juridica.backend.repository.ViviendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.Objects;

@Service
public class EncuestaService {

    private final FamiliaRepository familiaRepository;
    private final ViviendaRepository viviendaRepository;
    private final CaracteristicaViviendaRepository caracteristicaViviendaRepository;
    private final clinica_juridica.backend.repository.SolicitanteRepository solicitanteRepository;

    public EncuestaService(FamiliaRepository familiaRepository,
            ViviendaRepository viviendaRepository,
            CaracteristicaViviendaRepository caracteristicaViviendaRepository,
            clinica_juridica.backend.repository.SolicitanteRepository solicitanteRepository) {
        this.familiaRepository = familiaRepository;
        this.viviendaRepository = viviendaRepository;
        this.caracteristicaViviendaRepository = caracteristicaViviendaRepository;
        this.solicitanteRepository = solicitanteRepository;
    }

    @Transactional(readOnly = true)
    public DatosEncuestaResponse getEncuesta(String cedula) {
        Objects.requireNonNull(cedula, "Cedula cannot be null");
        DatosEncuestaResponse response = new DatosEncuestaResponse();

        // 1. Fetch Familia
        Optional<Familia> familiaOpt = familiaRepository.findById(cedula);
        if (familiaOpt.isPresent()) {
            Familia f = familiaOpt.get();
            DatosEncuestaRequest.FamiliaDTO fDto = new DatosEncuestaRequest.FamiliaDTO();
            fDto.setCantPersonas(f.getCantPersonas());
            fDto.setCantEstudiando(f.getCantEstudiando());
            fDto.setIngresoMes(f.getIngresoMes());
            fDto.setJefeFamilia(f.getJefeFamilia());
            fDto.setCantSinTrabajo(f.getCantSinTrabajo());
            fDto.setCantNinos(f.getCantNinos());
            fDto.setCantTrabaja(f.getCantTrabaja());
            fDto.setIdNivelEduJefe(f.getIdNivelEduJefe());
            fDto.setTiempoEstudio(f.getTiempoEstudio());
            response.setFamilia(fDto);
        }

        // 2. Fetch Vivienda
        Optional<Vivienda> viviendaOpt = viviendaRepository.findById(cedula);
        if (viviendaOpt.isPresent()) {
            Vivienda v = viviendaOpt.get();
            DatosEncuestaRequest.ViviendaDTO vDto = new DatosEncuestaRequest.ViviendaDTO();
            vDto.setCantHabitaciones(v.getCantHabitaciones());
            vDto.setCantBanos(v.getCantBanos());
            response.setVivienda(vDto);
        }

        // 3. Fetch Caracteristicas
        List<CaracteristicaVivienda> chars = caracteristicaViviendaRepository.findAllByCedula(cedula);
        List<CaracteristicaRequest> cDtos = new ArrayList<>();
        for (CaracteristicaVivienda c : chars) {
            cDtos.add(new CaracteristicaRequest(c.getIdTipoCat(), c.getIdCatVivienda()));
        }
        response.setCaracteristicas(cDtos);

        // 4. Fetch Solicitante fields (Condicion Laboral/Actividad)
        clinica_juridica.backend.models.Solicitante s = solicitanteRepository.findById(cedula).orElse(null);
        if (s != null) {
            response.setIdCondicion(s.getIdCondicion());
            response.setIdCondicionActividad(s.getIdCondicionActividad());
        }

        return response;
    }

    @Transactional
    public void saveEncuesta(String cedula, DatosEncuestaRequest request) {
        Objects.requireNonNull(cedula, "Cedula cannot be null");

        // 1. Save Familia
        if (request.getFamilia() != null) {
            DatosEncuestaRequest.FamiliaDTO fDto = request.getFamilia();
            Familia familia = familiaRepository.findById(cedula).orElse(null);
            if (familia == null) {
                familia = new Familia();
                familia.setCedula(cedula);
                familia.setIsNew(true);
            } else {
                familia.setIsNew(false);
            }
            familia.setCantPersonas(fDto.getCantPersonas());
            familia.setCantEstudiando(fDto.getCantEstudiando());
            familia.setIngresoMes(fDto.getIngresoMes());
            familia.setJefeFamilia(fDto.getJefeFamilia());
            familia.setCantSinTrabajo(fDto.getCantSinTrabajo());
            familia.setCantNinos(fDto.getCantNinos());
            familia.setCantTrabaja(fDto.getCantTrabaja());
            familia.setIdNivelEduJefe(fDto.getIdNivelEduJefe());
            familia.setTiempoEstudio(fDto.getTiempoEstudio());
            familiaRepository.save(familia);
        }

        // 2. Save Vivienda
        if (request.getVivienda() != null) {
            DatosEncuestaRequest.ViviendaDTO vDto = request.getVivienda();
            Vivienda vivienda = viviendaRepository.findById(cedula).orElse(null);
            if (vivienda == null) {
                vivienda = new Vivienda();
                vivienda.setCedula(cedula);
                vivienda.setIsNew(true);
            } else {
                vivienda.setIsNew(false);
            }
            vivienda.setCantHabitaciones(vDto.getCantHabitaciones());
            vivienda.setCantBanos(vDto.getCantBanos());
            viviendaRepository.save(vivienda);
        }

        // 3. Save Caracteristicas (Delete all and re-insert logic is safest for
        // checklists)
        if (request.getCaracteristicas() != null) {
            caracteristicaViviendaRepository.deleteAllByCedula(cedula);
            for (CaracteristicaRequest cDto : request.getCaracteristicas()) {
                if (cDto.getIdCatVivienda() != null && cDto.getIdTipoCat() != null) {
                    int idTipoCat = cDto.getIdTipoCat();
                    int idCatVivienda = cDto.getIdCatVivienda();
                    caracteristicaViviendaRepository.saveManual(cedula, idTipoCat, idCatVivienda);
                }
            }
        }

        // 4. Update Solicitante (Condicion Laboral/Actividad)
        clinica_juridica.backend.models.Solicitante s = solicitanteRepository.findById(cedula).orElse(null);
        if (s != null) {
            boolean updated = false;
            if (request.getIdCondicion() != null) {
                s.setIdCondicion(request.getIdCondicion());
                updated = true;
            }
            if (request.getIdCondicionActividad() != null) {
                s.setIdCondicionActividad(request.getIdCondicionActividad());
                updated = true;
            }
            if (updated) {
                solicitanteRepository.save(s);
            }
        }
    }
}
