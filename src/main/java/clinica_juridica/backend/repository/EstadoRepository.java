package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Estado;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoRepository extends CrudRepository<Estado, Integer> {
    @Override
    @Query("SELECT * FROM estados")
    List<Estado> findAll();

    @Override
    @Query("SELECT * FROM estados WHERE id_estado = :id")
    Optional<Estado> findById(Integer id);
}
