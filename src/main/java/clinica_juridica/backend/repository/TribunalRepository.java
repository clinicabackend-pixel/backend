package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Tribunal;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TribunalRepository extends CrudRepository<Tribunal, Integer> {
    @Override
    @Query("SELECT * FROM tribunal")
    List<Tribunal> findAll();

    @Override
    @Query("SELECT * FROM tribunal WHERE id_tribunal = :id")
    Optional<Tribunal> findById(Integer id);
}
