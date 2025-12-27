package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CategoriaVivienda;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaViviendaRepository extends CrudRepository<CategoriaVivienda, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM categorias_de_vivienda")
    List<CategoriaVivienda> findAll();

    @Query("SELECT * FROM categorias_de_vivienda WHERE estatus = :estatus")
    List<CategoriaVivienda> findAllByEstatus(String estatus);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("UPDATE categorias_de_vivienda SET estatus = :estatus WHERE id_tipo_cat = :idTipo AND id_cat_vivienda = :idCat")
    void updateStatus(Integer idTipo, Integer idCat, String estatus);

    @Query("SELECT COALESCE(MAX(id_cat_vivienda), 0) FROM categorias_de_vivienda WHERE id_tipo_cat = :idTipo")
    Integer findMaxIdByTipo(Integer idTipo);
}
