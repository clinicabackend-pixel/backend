package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CategoriaAmbitoLegal;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaAmbitoLegalRepository extends CrudRepository<CategoriaAmbitoLegal, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM categoria_ambito_legal")
    List<CategoriaAmbitoLegal> findAll();
}
