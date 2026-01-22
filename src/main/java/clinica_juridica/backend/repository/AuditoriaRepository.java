package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Auditoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AuditoriaRepository extends CrudRepository<Auditoria, Integer> {

    @Query("SELECT * FROM auditoria_sistema WHERE fecha_evento BETWEEN :inicio AND :fin")
    List<Auditoria> findByFechaEventoBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

}
