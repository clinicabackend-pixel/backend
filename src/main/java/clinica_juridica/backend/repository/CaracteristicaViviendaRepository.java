package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CaracteristicaVivienda;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaracteristicaViviendaRepository extends CrudRepository<CaracteristicaVivienda, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM caracteristicas_viviendas")
    List<CaracteristicaVivienda> findAll();

    @Query("SELECT * FROM caracteristicas_viviendas WHERE cedula = :cedula")
    List<CaracteristicaVivienda> findAllByCedula(@NonNull String cedula);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("DELETE FROM caracteristicas_viviendas WHERE cedula = :cedula")
    void deleteAllByCedula(@NonNull String cedula);
}
