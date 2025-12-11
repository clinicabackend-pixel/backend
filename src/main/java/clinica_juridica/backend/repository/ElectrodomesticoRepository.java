package clinica_juridica.backend.repository;

import clinica_juridica.backend.models.Electrodomestico;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectrodomesticoRepository extends CrudRepository<Electrodomestico, Integer> {
}
