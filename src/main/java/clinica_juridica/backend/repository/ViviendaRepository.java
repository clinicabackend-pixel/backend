package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Vivienda;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViviendaRepository extends CrudRepository<Vivienda, String> {
    @Override
    @NonNull
    @Query("SELECT * FROM viviendas")
    List<Vivienda> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM viviendas WHERE cedula = :id")
    Optional<Vivienda> findById(@NonNull String id);
}
