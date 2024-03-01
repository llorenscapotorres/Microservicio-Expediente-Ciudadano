package expediente.models.repositories;

import expediente.models.entities.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpedienteRepository extends JpaRepository<Expediente, Long> {
}
