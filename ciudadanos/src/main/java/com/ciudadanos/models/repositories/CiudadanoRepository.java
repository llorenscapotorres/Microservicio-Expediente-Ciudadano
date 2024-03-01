package com.ciudadanos.models.repositories;

import com.ciudadanos.models.entities.Ciudadano;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadanoRepository extends JpaRepository<Ciudadano, Long> {
}
