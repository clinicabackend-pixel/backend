package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Vivienda;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViviendaRepository extends CrudRepository<Vivienda, String> {
    @Override
    @Query("SELECT * FROM viviendas")
    List<Vivienda> findAll();

    @Override
    @Query("SELECT * FROM viviendas WHERE id_vivienda = :id")
    Optional<Vivienda> findById(String id);
}
