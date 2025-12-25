package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.NivelEducativo;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NivelEducativoRepository extends CrudRepository<NivelEducativo, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM niveles_educativos")
    List<NivelEducativo> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM niveles_educativos WHERE id_nivel_edu = :id")
    Optional<NivelEducativo> findById(@NonNull Integer id);
}
