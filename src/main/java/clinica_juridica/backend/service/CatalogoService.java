package clinica_juridica.backend.service;

import clinica_juridica.backend.dto.response.AmbitoLegalResponse;
import clinica_juridica.backend.dto.response.CategoriaViviendaResponse;
import clinica_juridica.backend.dto.response.TipoViviendaResponse;
import clinica_juridica.backend.dto.response.TribunalResponse;
import clinica_juridica.backend.models.AmbitoLegal;
import clinica_juridica.backend.models.CategoriaAmbitoLegal;
import clinica_juridica.backend.models.SubcategoriaAmbitoLegal;
import clinica_juridica.backend.models.CategoriaVivienda;
import clinica_juridica.backend.repository.AmbitoLegalRepository;
import clinica_juridica.backend.repository.CategoriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.CategoriaViviendaRepository;
import clinica_juridica.backend.repository.MateriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.SubcategoriaAmbitoLegalRepository;
import clinica_juridica.backend.repository.TipoCategoriaViviendaRepository;
import clinica_juridica.backend.repository.TribunalRepository;
import clinica_juridica.backend.dto.response.CondicionActividadResponse;
import clinica_juridica.backend.dto.response.CondicionLaboralResponse;
import clinica_juridica.backend.dto.response.NivelEducativoResponse;
import clinica_juridica.backend.models.CondicionActividad;
import clinica_juridica.backend.models.CondicionLaboral;
import clinica_juridica.backend.models.NivelEducativo;
import clinica_juridica.backend.models.TipoCategoriaVivienda;
import clinica_juridica.backend.repository.CondicionActividadRepository;
import clinica_juridica.backend.repository.CondicionLaboralRepository;
import clinica_juridica.backend.repository.NivelEducativoRepository;
import clinica_juridica.backend.repository.EstadoRepository;
import clinica_juridica.backend.repository.MunicipioRepository;
import clinica_juridica.backend.repository.ParroquiaRepository;
import clinica_juridica.backend.dto.response.EstadoResponse;
import clinica_juridica.backend.dto.response.MunicipioResponse;
import clinica_juridica.backend.dto.response.ParroquiaResponse;
import clinica_juridica.backend.dto.response.CentroResponse;
import clinica_juridica.backend.dto.request.CentroRequest;
import clinica_juridica.backend.models.Centro;
import clinica_juridica.backend.repository.CentroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogoService {

        private final MateriaAmbitoLegalRepository materiaRepository;
        private final CategoriaAmbitoLegalRepository categoriaRepository;
        private final SubcategoriaAmbitoLegalRepository subcategoriaRepository;
        private final AmbitoLegalRepository ambitoRepository;
        private final TribunalRepository tribunalRepository;
        private final TipoCategoriaViviendaRepository tipoCategoriaViviendaRepository;
        private final CategoriaViviendaRepository categoriaViviendaRepository;
        private final NivelEducativoRepository nivelEducativoRepository;
        private final CondicionLaboralRepository condicionLaboralRepository;
        private final CondicionActividadRepository condicionActividadRepository;
        private final EstadoRepository estadoRepository;
        private final MunicipioRepository municipioRepository;
        private final ParroquiaRepository parroquiaRepository;
        private final clinica_juridica.backend.repository.EstadoCivilRepository estadoCivilRepository;
        private final CentroRepository centroRepository;
        private final clinica_juridica.backend.repository.SemestreRepository semestreRepository;

        public CatalogoService(MateriaAmbitoLegalRepository materiaRepository,
                        CategoriaAmbitoLegalRepository categoriaRepository,
                        SubcategoriaAmbitoLegalRepository subcategoriaRepository,
                        AmbitoLegalRepository ambitoRepository,
                        TribunalRepository tribunalRepository,
                        TipoCategoriaViviendaRepository tipoCategoriaViviendaRepository,
                        CategoriaViviendaRepository categoriaViviendaRepository,
                        NivelEducativoRepository nivelEducativoRepository,
                        CondicionLaboralRepository condicionLaboralRepository,
                        CondicionActividadRepository condicionActividadRepository,
                        EstadoRepository estadoRepository,
                        MunicipioRepository municipioRepository,
                        ParroquiaRepository parroquiaRepository,
                        clinica_juridica.backend.repository.EstadoCivilRepository estadoCivilRepository,
                        CentroRepository centroRepository,
                        clinica_juridica.backend.repository.SemestreRepository semestreRepository) {
                this.materiaRepository = materiaRepository;
                this.categoriaRepository = categoriaRepository;
                this.subcategoriaRepository = subcategoriaRepository;
                this.ambitoRepository = ambitoRepository;
                this.tribunalRepository = tribunalRepository;
                this.tipoCategoriaViviendaRepository = tipoCategoriaViviendaRepository;
                this.categoriaViviendaRepository = categoriaViviendaRepository;
                this.nivelEducativoRepository = nivelEducativoRepository;
                this.condicionLaboralRepository = condicionLaboralRepository;
                this.condicionActividadRepository = condicionActividadRepository;
                this.estadoRepository = estadoRepository;
                this.municipioRepository = municipioRepository;
                this.parroquiaRepository = parroquiaRepository;
                this.estadoCivilRepository = estadoCivilRepository;
                this.centroRepository = centroRepository;
                this.semestreRepository = semestreRepository;
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
                                                AmbitoLegal::getCodSubAmbLegal,
                                                Collectors.mapping(
                                                                a -> new AmbitoLegalResponse(a.getCodAmbLegal(),
                                                                                a.getAmbLegal(), "AMBITO", null),
                                                                Collectors.toList())));

                // Map Subcategories by CategoriaID (Level 3 grouped by Level 2), attaching
                // Ambitos
                var subMap = subcategorias.stream().collect(
                                Collectors.groupingBy(
                                                SubcategoriaAmbitoLegal::getCodCatAmbLegal,
                                                Collectors.mapping(
                                                                s -> new AmbitoLegalResponse(
                                                                                s.getCodSubAmbLegal(),
                                                                                s.getNombreSubcategoria(),
                                                                                "SUBCATEGORIA",
                                                                                ambitoMap.getOrDefault(
                                                                                                s.getCodSubAmbLegal(),
                                                                                                Collections.emptyList())),
                                                                Collectors.toList())));

                // Map Categories by MateriaID (Level 2 grouped by Level 1), attaching
                // Subcategories
                var catMap = categorias.stream().collect(
                                Collectors.groupingBy(
                                                CategoriaAmbitoLegal::getCodMatAmbLegal,
                                                Collectors.mapping(
                                                                c -> new AmbitoLegalResponse(
                                                                                c.getCodCatAmbLegal(),
                                                                                c.getCatAmbLegal(),
                                                                                "CATEGORIA",
                                                                                subMap.getOrDefault(
                                                                                                c.getCodCatAmbLegal(),
                                                                                                Collections.emptyList())),
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

        public List<TipoViviendaResponse> getViviendas(String estatus) {
                var tipos = tipoCategoriaViviendaRepository.findAll();
                List<CategoriaVivienda> categorias;

                if (estatus != null && !estatus.isBlank()) {
                        categorias = categoriaViviendaRepository.findAllByEstatus(estatus);
                } else {
                        categorias = categoriaViviendaRepository.findAll();
                }

                var catMap = categorias.stream().collect(
                                Collectors.groupingBy(
                                                CategoriaVivienda::getIdTipoCat,
                                                Collectors.mapping(
                                                                c -> new CategoriaViviendaResponse(
                                                                                c.getIdCatVivienda(),
                                                                                c.getDescripcion(),
                                                                                c.getEstatus()),
                                                                Collectors.toList())));

                return tipos.stream()
                                .map(t -> new TipoViviendaResponse(
                                                t.getIdTipoCat(),
                                                t.getTipoCategoria(),
                                                catMap.getOrDefault(t.getIdTipoCat(), Collections.emptyList())))
                                .toList();
        }

        public List<NivelEducativoResponse> getNivelesEducativos(String estatus) {
                List<NivelEducativo> list;
                if (estatus != null && !estatus.isBlank()) {
                        list = nivelEducativoRepository.findAllByEstatus(estatus);
                } else {
                        list = nivelEducativoRepository.findAll();
                }
                return list.stream()
                                .map(n -> new NivelEducativoResponse(
                                                n.getIdNivelEdu(),
                                                n.getNivel()))
                                .toList();
        }

        public List<CondicionLaboralResponse> getCondicionesLaborales(String estatus) {
                List<CondicionLaboral> list;
                if (estatus != null && !estatus.isBlank()) {
                        list = condicionLaboralRepository.findAllByEstatus(estatus);
                } else {
                        list = condicionLaboralRepository.findAll();
                }
                return list.stream()
                                .map(c -> new CondicionLaboralResponse(
                                                c.getIdCondicion(),
                                                c.getCondicion()))
                                .toList();
        }

        public List<CondicionActividadResponse> getCondicionesActividad(String estatus) {
                List<CondicionActividad> list;
                if (estatus != null && !estatus.isBlank()) {
                        list = condicionActividadRepository.findAllByEstatus(estatus);
                } else {
                        list = condicionActividadRepository.findAll();
                }
                return list.stream()
                                .map(c -> new CondicionActividadResponse(
                                                c.getIdCondicionActividad(),
                                                c.getNombreActividad()))
                                .toList();
        }

        public List<EstadoResponse> getEstados() {
                return estadoRepository.findAll().stream()
                                .map(e -> new EstadoResponse(e.getIdEstado(), e.getNombreEstado()))
                                .toList();
        }

        public List<MunicipioResponse> getMunicipios(Integer idEstado) {
                return municipioRepository.findAllByIdEstado(idEstado).stream()
                                .map(m -> new MunicipioResponse(m.getIdMunicipio(), m.getNombreMunicipio(),
                                                m.getIdEstado()))
                                .toList();
        }

        public List<ParroquiaResponse> getParroquias(Integer idMunicipio) {
                return parroquiaRepository.findAllByIdMunicipio(idMunicipio).stream()
                                .map(p -> new ParroquiaResponse(p.getIdParroquia(), p.getNombreParroquia(),
                                                p.getIdMunicipio()))
                                .toList();
        }

        public List<MunicipioResponse> getAllMunicipios() {
                return municipioRepository.findAll().stream()
                                .map(m -> new MunicipioResponse(m.getIdMunicipio(), m.getNombreMunicipio(),
                                                m.getIdEstado()))
                                .toList();
        }

        public List<ParroquiaResponse> getAllParroquias() {
                return parroquiaRepository.findAll().stream()
                                .map(p -> new ParroquiaResponse(p.getIdParroquia(), p.getNombreParroquia(),
                                                p.getIdMunicipio()))
                                .toList();
        }

        public List<clinica_juridica.backend.dto.response.EstadoCivilResponse> getEstadosCiviles() {
                return estadoCivilRepository.findAll().stream()
                                .map(e -> new clinica_juridica.backend.dto.response.EstadoCivilResponse(
                                                e.getIdEstadoCivil(),
                                                e.getDescripcion()))
                                .toList();
        }

        @Transactional
        public void updateNivelEducativoStatus(Integer id, String estatus) {
                nivelEducativoRepository.updateStatus(id, estatus);
        }

        @Transactional
        public void updateCondicionLaboralStatus(Integer id, String estatus) {
                condicionLaboralRepository.updateStatus(id, estatus);
        }

        @Transactional
        public void updateCondicionActividadStatus(Integer id, String estatus) {
                condicionActividadRepository.updateStatus(id, estatus);
        }

        @Transactional
        public void updateCategoriaViviendaStatus(Integer idTipo, Integer idCat, String estatus) {
                categoriaViviendaRepository.updateStatus(idTipo, idCat, estatus);
        }

        @Transactional
        public void createNivelEducativo(String nombre) {
                // ID is Serial, auto-generated.
                NivelEducativo nuevo = new NivelEducativo(null, nombre, "ACTIVO");
                nivelEducativoRepository.save(nuevo);
        }

        @Transactional
        public void createCondicionLaboral(String nombre) {
                CondicionLaboral nuevo = new CondicionLaboral(null, nombre, "ACTIVO");
                condicionLaboralRepository.save(nuevo);
        }

        @Transactional
        public void createCondicionActividad(String nombre) {
                CondicionActividad nuevo = new CondicionActividad(null, nombre, "ACTIVO");
                condicionActividadRepository.save(nuevo);
        }

        @Transactional
        public void createTipoVivienda(String nombreTipo) {
                if (nombreTipo == null) {
                        throw new IllegalArgumentException("nombreTipo cannot be null");
                }
                // Check if exists
                var existing = tipoCategoriaViviendaRepository.findByTipoCategoria(nombreTipo);
                if (existing != null) {
                        throw new IllegalArgumentException("El tipo de vivienda ya existe: " + nombreTipo);
                }

                Integer maxId = tipoCategoriaViviendaRepository.findMaxId();
                Integer idTipo = (maxId != null ? maxId : 0) + 1;
                TipoCategoriaVivienda nuevoTipo = new TipoCategoriaVivienda(idTipo, nombreTipo);
                tipoCategoriaViviendaRepository.save(nuevoTipo);
        }

        @Transactional
        public void createVivienda(Integer idTipo, String descripcion) {
                if (idTipo == null) {
                        throw new IllegalArgumentException("idTipo cannot be null");
                }
                // 1. Verify Tipo exists
                var tipoOptional = tipoCategoriaViviendaRepository.findById(idTipo);
                if (tipoOptional.isEmpty()) {
                        throw new IllegalArgumentException("El Tipo de Vivienda con ID " + idTipo + " no existe.");
                }

                // 2. Create Categoria
                Integer maxId = categoriaViviendaRepository.findMaxIdByTipo(idTipo);
                Integer idCat = (maxId != null ? maxId : 0) + 1;
                CategoriaVivienda nuevaCat = new CategoriaVivienda(idCat, idTipo, descripcion, "ACTIVO");
                categoriaViviendaRepository.save(nuevaCat);
        }

        public List<CentroResponse> getCentros() {
                return centroRepository.findAll().stream()
                                .map(c -> new CentroResponse(c.getIdCentro(), c.getNombre(), c.getIdParroquia()))
                                .toList();
        }

        @Transactional
        public void createCentro(CentroRequest request) {
                Centro centro = new Centro();
                centro.setNombre(request.nombreCentro());
                centro.setIdParroquia(request.idParroquia()); // Puede ser nulo
                centroRepository.save(centro);
        }

        @Transactional
        public void updateCentro(@NonNull Integer id, CentroRequest request) {
                Centro centro = centroRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Centro no encontrado con id: " + id));

                centro.setNombre(request.nombreCentro());
                if (request.idParroquia() != null) {
                        centro.setIdParroquia(request.idParroquia());
                }
                centroRepository.save(centro);
        }

        public List<clinica_juridica.backend.dto.response.SemestreResponse> getSemestres() {
                return semestreRepository.findAll().stream()
                                .map(s -> new clinica_juridica.backend.dto.response.SemestreResponse(
                                                s.getTermino(),
                                                s.getNombre()))
                                .toList();
        }
}
