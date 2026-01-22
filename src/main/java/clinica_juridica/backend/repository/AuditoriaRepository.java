package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Auditoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends CrudRepository<Auditoria, Integer> {

    List<Auditoria> findByFechaEventoBetween(LocalDateTime inicio, LocalDateTime fin);

}
