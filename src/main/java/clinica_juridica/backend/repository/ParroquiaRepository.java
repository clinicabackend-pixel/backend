package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Parroquia;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParroquiaRepository extends CrudRepository<Parroquia, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM parroquias")
    List<Parroquia> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM parroquias WHERE id_parroquia = :id")
    Optional<Parroquia> findById(@NonNull Integer id);
}
