package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Documento;
import org.springframework.lang.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends CrudRepository<Documento, Integer> {
    @Override
    @NonNull
    @Query("SELECT * FROM documentos")
    List<Documento> findAll();

    @Query("SELECT COALESCE(MAX(id_documento), 0) FROM documentos WHERE num_caso = :numCaso")
    Integer findMaxIdByNumCaso(String numCaso);

    @org.springframework.data.jdbc.repository.query.Modifying
    @Query("INSERT INTO documentos (id_documento, num_caso, fecha_registro, folio_ini, folio_fin, titulo, observacion, username) VALUES (:id, :numCaso, :fechaRegistro, :folioIni, :folioFin, :titulo, :observacion, :username)")
    void saveManual(Integer id, String numCaso, java.time.LocalDate fechaRegistro, Integer folioIni, Integer folioFin,
            String titulo, String observacion, String username);
}
