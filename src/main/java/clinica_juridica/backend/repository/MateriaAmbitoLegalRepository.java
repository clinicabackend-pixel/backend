package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.MateriaAmbitoLegal;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaAmbitoLegalRepository extends CrudRepository<MateriaAmbitoLegal, Integer> {
    @Override
    @Query("SELECT * FROM materia_ambito_legal")
    List<MateriaAmbitoLegal> findAll();
}
