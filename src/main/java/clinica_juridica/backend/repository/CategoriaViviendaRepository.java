package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CategoriaVivienda;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaViviendaRepository extends CrudRepository<CategoriaVivienda, Integer> {
    @Override
    @Query("SELECT * FROM categorias_de_vivienda")
    List<CategoriaVivienda> findAll();
}
