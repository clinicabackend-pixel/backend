package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.response.AmbitoLegalResponse;
import clinica_juridica.backend.dto.response.TribunalResponse;
import clinica_juridica.backend.repository.AmbitoLegalRepository;
import clinica_juridica.backend.repository.CategoriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.MateriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.SubcategoriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.TribunalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class CatalogoService {

    private final MateriaAmbitoLegalRepository materiaRepository;
    private final CategoriaAmbitoLegalRepository categoriaRepository;
    private final SubcategoriaAmbitoLegalRepository subcategoriaRepository;
    private final AmbitoLegalRepository ambitoRepository;
    private final TribunalRepository tribunalRepository;

    public CatalogoService(MateriaAmbitoLegalRepository materiaRepository,
            CategoriaAmbitoLegalRepository categoriaRepository,
            SubcategoriaAmbitoLegalRepository subcategoriaRepository,
            AmbitoLegalRepository ambitoRepository,
            TribunalRepository tribunalRepository) {
        this.materiaRepository = materiaRepository;
        this.categoriaRepository = categoriaRepository;
        this.subcategoriaRepository = subcategoriaRepository;
        this.ambitoRepository = ambitoRepository;
        this.tribunalRepository = tribunalRepository;
    }

    public List<AmbitoLegalResponse> getAmbitosLegalesTree() {
        // 1. Fetch all raw data (4 queries total)
        var materias = materiaRepository.findAll();
        var categorias = categoriaRepository.findAll();
        var subcategorias = subcategoriaRepository.findAll();
        var ambitos = ambitoRepository.findAll();

        // 2. Build Tree
        // Map Ambitos by SubcategoriaID (Level 4 grouped by Level 3)
        var ambitoMap = ambitos.stream().collect(
                Collectors.groupingBy(
                        clinica_juridica.backend.models.AmbitoLegal::getCodSubAmbLegal,
                        Collectors.mapping(
                                a -> new AmbitoLegalResponse(a.getCodAmbLegal(), a.getAmbLegal(), "AMBITO", null),
                                Collectors.toList())));

        // Map Subcategories by CategoriaID (Level 3 grouped by Level 2), attaching
        // Ambitos
        var subMap = subcategorias.stream().collect(
                Collectors.groupingBy(
                        clinica_juridica.backend.models.SubcategoriaAmbitoLegal::getCodCatAmbLegal,
                        Collectors.mapping(
                                s -> new AmbitoLegalResponse(
                                        s.getCodSubAmbLegal(),
                                        s.getNombreSubcategoria(),
                                        "SUBCATEGORIA",
                                        ambitoMap.getOrDefault(s.getCodSubAmbLegal(), Collections.emptyList())),
                                Collectors.toList())));

        // Map Categories by MateriaID (Level 2 grouped by Level 1), attaching
        // Subcategories
        var catMap = categorias.stream().collect(
                Collectors.groupingBy(
                        clinica_juridica.backend.models.CategoriaAmbitoLegal::getCodMatAmbLegal,
                        Collectors.mapping(
                                c -> new AmbitoLegalResponse(
                                        c.getCodCatAmbLegal(),
                                        c.getCatAmbLegal(),
                                        "CATEGORIA",
                                        subMap.getOrDefault(c.getCodCatAmbLegal(), Collections.emptyList())),
                                Collectors.toList())));

        // Build Materia Nodes (Level 1), attaching Categories
        return materias.stream()
                .map(m -> new AmbitoLegalResponse(
                        m.getCodMatAmbLegal(),
                        m.getMatAmbLegal(),
                        "MATERIA",
                        catMap.getOrDefault(m.getCodMatAmbLegal(), Collections.emptyList())))
                .toList();
    }

    public List<TribunalResponse> getTribunales() {
        return tribunalRepository.findAll().stream()
                .map(t -> new TribunalResponse(
                        t.getIdTribunal(),
                        t.getMateria(), // Map 'materia' to 'tipoTribunal'
                        t.getNombreTribunal()))
                .toList();
    }
}
