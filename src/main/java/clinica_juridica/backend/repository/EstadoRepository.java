package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Estado;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoRepository extends CrudRepository<Estado, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM estados")
    List<Estado> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM estados WHERE id_estado = :id")
    Optional<Estado> findById(@NonNull Integer id);
}
