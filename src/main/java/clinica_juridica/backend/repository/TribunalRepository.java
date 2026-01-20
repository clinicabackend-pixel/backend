package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Tribunal;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TribunalRepository extends CrudRepository<Tribunal, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM tribunal")
    List<Tribunal> findAll();

    @Override
    @NonNull
    @Query("SELECT * FROM tribunal WHERE id_tribunal = :id")
    Optional<Tribunal> findById(@NonNull Integer id);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("UPDATE tribunal SET estatus = :status WHERE id_tribunal = :id")
    void updateStatus(Integer id, String status);
}
