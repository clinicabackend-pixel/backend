package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.CasoAsignado;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jdbc.repository.query.Modifying;
import java.util.List;

import clinica_juridica.backend.dto.projection.CasoAsignadoProjection;

@Repository
public interface CasoAsignadoRepository extends CrudRepository<CasoAsignado, Integer> {
        @Override
        @NonNull
        @Query("SELECT * FROM casos_asignados")
        List<CasoAsignado> findAll();

        @Query("SELECT ca.num_caso AS numCaso, ca.username, ca.termino, u.nombre AS nombre FROM casos_asignados ca INNER JOIN usuarios u ON ca.username = u.username WHERE ca.num_caso = :numCaso AND ca.termino = (SELECT MAX(termino) FROM casos_asignados WHERE num_caso = :numCaso)")
        List<CasoAsignadoProjection> findAllByNumCaso(@Param("numCaso") String numCaso);

        @Modifying
        @Query("INSERT INTO casos_asignados (num_caso, username, termino) VALUES (:numCaso, :username, :termino)")
        void saveManual(@Param("numCaso") String numCaso, @Param("username") String username,
                        @Param("termino") String termino);

        @Modifying
        @Query("DELETE FROM casos_asignados WHERE num_caso = :numCaso AND username = :username AND termino = :termino")
        void deleteManual(@Param("numCaso") String numCaso, @Param("username") String username,
                        @Param("termino") String termino);
}
