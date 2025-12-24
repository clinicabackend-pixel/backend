
package clinica_juridica.backend.service;

import org.springframework.stereotype.Service;

@Service
public class CasoService {

        public CasoService() {
        }

        /*
         * public List<CasoListResponse> findAllWithSolicitanteInfo() {
         * var casos = casoRepository.findAllWithSolicitanteInfo();
         * if (casos.isEmpty()) {
         * return List.of();
         * }
         * 
         * var numCasos =
         * casos.stream().map(clinica_juridica.backend.dto.projection.CasoListProjection
         * ::numCaso).toList();
         * var estudiantesMap =
         * asignacionRepository.findNombresByNumCasos(numCasos).stream()
         * .collect(java.util.stream.Collectors.groupingBy(
         * clinica_juridica.backend.dto.projection.NombreEstudianteProjection::numCaso,
         * java.util.stream.Collectors.mapping(
         * p -> new clinica_juridica.backend.dto.response.EstudianteResumidoResponse(
         * p.idEstudiante(),
         * p.nombre()),
         * java.util.stream.Collectors.toList())));
         * 
         * return casos.stream().map(c -> new CasoListResponse(
         * c.numCaso(),
         * c.materia(),
         * c.cedula(),
         * c.nombre(),
         * c.fecha(),
         * c.estatus(),
         * estudiantesMap.getOrDefault(c.numCaso(), List.of()))).toList();
         * }
         */

        /*
         * public List<CasoListResponse> getCasosByStatus(String status) {
         * var casos = casoRepository.findAllByEstatus(status);
         * if (casos.isEmpty()) {
         * return List.of();
         * }
         * 
         * var numCasos =
         * casos.stream().map(clinica_juridica.backend.dto.projection.CasoListProjection
         * ::numCaso).toList();
         * var estudiantesMap =
         * asignacionRepository.findNombresByNumCasos(numCasos).stream()
         * .collect(java.util.stream.Collectors.groupingBy(
         * clinica_juridica.backend.dto.projection.NombreEstudianteProjection::numCaso,
         * java.util.stream.Collectors.mapping(
         * p -> new clinica_juridica.backend.dto.response.EstudianteResumidoResponse(
         * p.idEstudiante(),
         * p.nombre()),
         * java.util.stream.Collectors.toList())));
         * 
         * return casos.stream().map(c -> new CasoListResponse(
         * c.numCaso(),
         * c.materia(),
         * c.cedula(),
         * c.nombre(),
         * c.fecha(),
         * c.estatus(),
         * estudiantesMap.getOrDefault(c.numCaso(), List.of()))).toList();
         * }
         */

        /*
         * public String createCaso(Caso caso) {
         * return casoRepository.createCaso(caso);
         * }
         * public String updateCaso(Caso caso) {
         * return casoRepository.updateCaso(caso);
         * }
         */

        /*
         * public clinica_juridica.backend.dto.response.CasoDetalleResponse
         * getCasoDetalle(String numCaso) {
         * var basicInfo = casoRepository.findCasoDetalleBasic(numCaso)
         * .orElseThrow(() -> new RuntimeException("Caso no encontrado: " + numCaso));
         * 
         * var asignaciones = asignacionRepository.findByNumCaso(numCaso);
         * var beneficiarios = beneficiarioCasoRepository.findByNumCaso(numCaso);
         * var citas = citaRepository.findByNumCaso(numCaso);
         * var acciones = accionRepository.findByNumCaso(numCaso);
         * var expediente =
         * expedienteTribunalRepository.findByNumCaso(numCaso).orElse(null);
         * 
         * return new clinica_juridica.backend.dto.response.CasoDetalleResponse(
         * basicInfo.numCaso(),
         * basicInfo.fechaRecepcion(),
         * basicInfo.estatus(),
         * basicInfo.sintesis(),
         * basicInfo.cantBeneficiarios(),
         * basicInfo.idSolicitante(),
         * basicInfo.nombreSolicitante(),
         * basicInfo.materia(),
         * basicInfo.nombreCentro(),
         * expediente,
         * beneficiarios,
         * asignaciones,
         * citas,
         * acciones);
         * }
         */
}
