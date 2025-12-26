package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.SubcategoriaAmbitoLegal;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoriaAmbitoLegalRepository extends CrudRepository<SubcategoriaAmbitoLegal, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM subcategoria_ambito_legal")
    List<SubcategoriaAmbitoLegal> findAll();

    @Query("SELECT * FROM subcategoria_ambito_legal WHERE cod_cat_amb_legal = :codCatAmbLegal")
    List<SubcategoriaAmbitoLegal> findByCodCatAmbLegal(Integer codCatAmbLegal);
}
