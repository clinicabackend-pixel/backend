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

        @Query("SELECT cs.num_caso AS numCaso, cs.username AS username, cs.termino AS termino, u.nombre AS nombre FROM casos_supervisados cs INNER JOIN usuarios u ON cs.username = u.username WHERE cs.num_caso = :numCaso")
        List<CasoSupervisadoProjection> findAllByNumCaso(@Param("numCaso") String numCaso);

        @Modifying
        @Query("INSERT INTO casos_supervisados (num_caso, username, termino) VALUES (:numCaso, :username, :termino)")
        void saveManual(@Param("numCaso") String numCaso, @Param("username") String username,
                        @Param("termino") String termino);

        @Query("SELECT COUNT(*) > 0 FROM casos_supervisados WHERE num_caso = :numCaso AND username = :username AND termino = :termino")
        boolean existsByNumCasoAndUsernameAndTermino(@Param("numCaso") String numCaso,
                        @Param("username") String username,
                        @Param("termino") String termino);

        @Modifying
        @Query("DELETE FROM casos_supervisados WHERE num_caso = :numCaso AND username = :username AND termino = :termino")
        void deleteManual(@Param("numCaso") String numCaso, @Param("username") String username,
                        @Param("termino") String termino);
}
