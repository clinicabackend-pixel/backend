package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.TipoCategoriaVivienda;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoCategoriaViviendaRepository extends CrudRepository<TipoCategoriaVivienda, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM tipos_categorias_viviendas")
    List<TipoCategoriaVivienda> findAll();

    @Query("SELECT * FROM tipos_categorias_viviendas WHERE tipo_categoria = :nombre")
    TipoCategoriaVivienda findByTipoCategoria(String nombre);

    @Query("SELECT COALESCE(MAX(id_tipo_cat), 0) FROM tipos_categorias_viviendas")
    Integer findMaxId();
}
