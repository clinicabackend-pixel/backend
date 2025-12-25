package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Centro;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CentroRepository extends CrudRepository<Centro, Integer> {
    @Override
    @Query("SELECT * FROM centros")
    List<Centro> findAll();

    @Override
    @Query("SELECT * FROM centros WHERE id_centro = :id")
    Optional<Centro> findById(Integer id);
}
