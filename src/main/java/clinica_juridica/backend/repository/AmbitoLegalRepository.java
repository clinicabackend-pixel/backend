package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.AmbitoLegal;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbitoLegalRepository extends CrudRepository<AmbitoLegal, Integer> {
    @Override
    @Query("SELECT * FROM ambito_legal")
    List<AmbitoLegal> findAll();
}
