package clinica_juridica.backend.service;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioPerfilService {

    private final UsuarioRepository usuarioRepository;
    private final SolicitanteRepository solicitanteRepository;
    private final NivelEducativoRepository nivelEducativoRepository;
    private final ViviendaRepository viviendaRepository;
    private final TrabajoRepository trabajoRepository;
    private final ElectrodomesticoRepository electrodomesticoRepository;
    private final FamiliaRepository familiaRepository;
    private final TelefonoRepository telefonoRepository;
    private final ElectrodomesticoSolicitanteRepository electrodomesticoSolicitanteRepository;

    public UsuarioPerfilService(UsuarioRepository usuarioRepository,
            SolicitanteRepository solicitanteRepository,
            NivelEducativoRepository nivelEducativoRepository,
            ViviendaRepository viviendaRepository,
            TrabajoRepository trabajoRepository,
            ElectrodomesticoRepository electrodomesticoRepository,
            FamiliaRepository familiaRepository,
            TelefonoRepository telefonoRepository,
            ElectrodomesticoSolicitanteRepository electrodomesticoSolicitanteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.solicitanteRepository = solicitanteRepository;
        this.nivelEducativoRepository = nivelEducativoRepository;
        this.viviendaRepository = viviendaRepository;
        this.trabajoRepository = trabajoRepository;
        this.electrodomesticoRepository = electrodomesticoRepository;
        this.familiaRepository = familiaRepository;
        this.telefonoRepository = telefonoRepository;
        this.electrodomesticoSolicitanteRepository = electrodomesticoSolicitanteRepository;
    }

    public Iterable<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Iterable<Solicitante> findAllSolicitantes() {
        return solicitanteRepository.findAll();
    }
}
