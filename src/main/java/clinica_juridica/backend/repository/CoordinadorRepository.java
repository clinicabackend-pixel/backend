package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Coordinador;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoordinadorRepository extends CrudRepository<Coordinador, String> {
    @Override
    @NonNull
    @Query("SELECT * FROM coordinadores")
    List<Coordinador> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM coordinadores WHERE cedula = :cedula")
    Optional<Coordinador> findById(@NonNull String cedula);
}
