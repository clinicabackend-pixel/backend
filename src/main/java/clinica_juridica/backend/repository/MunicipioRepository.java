package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Municipio;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MunicipioRepository extends CrudRepository<Municipio, Integer> {
    @Override
    @Query("SELECT * FROM municipios")
    List<Municipio> findAll();

    @Override
    @Query("SELECT * FROM municipios WHERE id_municipio = :id")
    Optional<Municipio> findById(Integer id);
}
