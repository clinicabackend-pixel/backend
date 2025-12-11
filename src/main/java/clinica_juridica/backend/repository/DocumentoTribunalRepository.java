package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.DocumentoTribunal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoTribunalRepository extends CrudRepository<DocumentoTribunal, String> {
}
