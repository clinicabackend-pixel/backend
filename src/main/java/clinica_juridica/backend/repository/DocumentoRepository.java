package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Documento;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends CrudRepository<Documento, Integer> {
    @Override
    @Query("SELECT * FROM documentos")
    List<Documento> findAll();
}
