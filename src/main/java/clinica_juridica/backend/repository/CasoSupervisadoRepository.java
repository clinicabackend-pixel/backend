package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CasoSupervisado;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import java.util.List;

import clinica_juridica.backend.dto.projection.CasoSupervisadoProjection;

@Repository
public interface CasoSupervisadoRepository extends CrudRepository<CasoSupervisado, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM casos_supervisados")
    List<CasoSupervisado> findAll();

    @Query("SELECT cs.num_caso, cs.username, cs.termino, u.nombre FROM casos_supervisados cs INNER JOIN usuarios u ON cs.username = u.username WHERE cs.num_caso = :numCaso AND cs.termino = (SELECT MAX(termino) FROM casos_supervisados WHERE num_caso = :numCaso)")
    List<CasoSupervisadoProjection> findAllByNumCaso(@Param("numCaso") String numCaso);

    @Modifying
    @Query("INSERT INTO casos_supervisados (num_caso, username, termino) VALUES (:numCaso, :username, :termino)")
    void saveManual(@Param("numCaso") String numCaso, @Param("username") String username,
            @Param("termino") String termino);
}
