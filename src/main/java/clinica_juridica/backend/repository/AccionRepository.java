package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Accion;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccionRepository extends CrudRepository<Accion, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM accion")
    List<Accion> findAll();
}
