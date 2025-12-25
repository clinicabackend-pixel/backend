package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Caso;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CasoRepository extends CrudRepository<Caso, String> {
    @Override
    @Query("SELECT * FROM casos")
    List<Caso> findAll();

    @Override
    @Query("SELECT * FROM casos WHERE num_caso = :numCaso")
    Optional<Caso> findById(String numCaso);

    @Query("SELECT * FROM casos WHERE estatus = :estatus")
    List<Caso> findAllByEstatus(String estatus);
}
