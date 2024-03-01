package expediente.models.repositories;

import expediente.models.entities.ExpedienteCiudadano;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpedienteCiudadanoRepository extends JpaRepository<ExpedienteCiudadano, Long> {
}
