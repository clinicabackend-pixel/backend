package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Familia;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamiliaRepository extends CrudRepository<Familia, String> {
    @Override
    @NonNull
    @Query("SELECT * FROM familias")
    List<Familia> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM familias WHERE cedula = :cedula")
    Optional<Familia> findById(@NonNull String cedula);
}
