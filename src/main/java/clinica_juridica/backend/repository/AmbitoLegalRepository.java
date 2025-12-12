package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.AmbitoLegal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AmbitoLegalRepository extends CrudRepository<AmbitoLegal, Integer> {

    @Query("SELECT * FROM ambitos_legales WHERE materia = :materia")
    List<AmbitoLegal> buscarPorMateria(@Param("materia") String materia);
}
