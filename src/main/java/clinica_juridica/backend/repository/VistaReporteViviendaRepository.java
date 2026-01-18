package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.VistaReporteVivienda;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

@Repository
public interface VistaReporteViviendaRepository extends CrudRepository<VistaReporteVivienda, String> {

    @Override
    @NonNull
    @Query("SELECT * FROM vista_reporte_vivienda")
    List<VistaReporteVivienda> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM vista_reporte_vivienda WHERE cedula = :cedula")
    Optional<VistaReporteVivienda> findById(@NonNull String cedula);
}
