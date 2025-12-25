package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CaracteristicaVivienda;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaracteristicaViviendaRepository extends CrudRepository<CaracteristicaVivienda, Integer> {
    @Override
    @Query("SELECT * FROM caracteristicas_viviendas")
    List<CaracteristicaVivienda> findAll();
}
