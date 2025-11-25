package clinica_juridica.backend.application.usecase;

import clinica_juridica.backend.application.port.output.CasoRepository;
import clinica_juridica.backend.domain.entities.Caso;

import java.time.LocalDate;

/**
 * Caso de uso para crear/registrar un nuevo caso.
 * Aplica las reglas de negocio y delega la persistencia al repositorio.
 */
public class RegistrarCaso {
    
    private final CasoRepository casoRepository;
    
    public RegistrarCaso(CasoRepository casoRepository) {
        this.casoRepository = casoRepository;
    }
    
    /**
     * Ejecuta el caso de uso de creación de caso.
     * @param comando Comando con los datos necesarios para crear el caso
     * @return El caso creado con su ID asignado
     */
    public Caso ejecutar(ComandoCrearCaso comando) {
        // Validaciones básicas
        if (comando.sintesis() == null || comando.sintesis().isBlank()) {
            throw new IllegalArgumentException("La síntesis del caso no puede estar vacía");
        }
        
        if (comando.idSolicitante() == null || comando.idSolicitante().isBlank()) {
            throw new IllegalArgumentException("El ID del solicitante es obligatorio");
        }
        
        // Crear la entidad Caso aplicando reglas de negocio
        Caso nuevoCaso = new Caso();
        nuevoCaso.setSintesis(comando.sintesis());
        nuevoCaso.setEstatus("ABIERTO"); // Regla de negocio: estatus por defecto
        nuevoCaso.setFechaRecepcion(LocalDate.now()); // Regla de negocio: fecha actual
        nuevoCaso.setIdSolicitante(comando.idSolicitante());
        nuevoCaso.setTramite(comando.tramite());
        nuevoCaso.setCantBeneficiarios(comando.cantBeneficiarios());
        nuevoCaso.setIdCentro(comando.idCentro());
        nuevoCaso.setIdAmbitoLegal(comando.idAmbitoLegal());
        
        // Persistir a través del puerto de salida
        return casoRepository.guardar(nuevoCaso);
    }
    
    /**
     * Comando (DTO) que encapsula los datos necesarios para crear un caso.
     */
    public record ComandoCrearCaso(
            String sintesis,
            String idSolicitante,
            String tramite,
            Integer cantBeneficiarios,
            Integer idCentro,
            Integer idAmbitoLegal
    ) {}
}
