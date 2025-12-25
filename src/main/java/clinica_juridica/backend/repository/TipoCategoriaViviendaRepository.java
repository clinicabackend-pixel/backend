package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.TipoCategoriaVivienda;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoCategoriaViviendaRepository extends CrudRepository<TipoCategoriaVivienda, Integer> {
    @Override
    @Query("SELECT * FROM tipos_categorias_viviendas")
    List<TipoCategoriaVivienda> findAll();
}
