package clinica_juridica.backend.service;

import clinica_juridica.backend.models.*;
import clinica_juridica.backend.repository.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioPerfilService {

    private final UsuarioRepository usuarioRepository;
    private final SolicitanteRepository solicitanteRepository;

    public UsuarioPerfilService(UsuarioRepository usuarioRepository,
            SolicitanteRepository solicitanteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.solicitanteRepository = solicitanteRepository;
    }

    public Iterable<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Iterable<Solicitante> findAllSolicitantes() {
        return solicitanteRepository.findAll();
    }
}
