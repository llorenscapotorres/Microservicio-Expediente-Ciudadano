package com.ciudadanos.models.services;

import com.ciudadanos.models.entities.Ciudadano;
import com.ciudadanos.models.repositories.CiudadanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadanoServiceImpl {

    @Autowired
    CiudadanoRepository ciudadanoRepository;

    public List<Ciudadano> listar() {
        return ciudadanoRepository.findAll();
    }

    public Long darDeAlta(Ciudadano input) {
        Ciudadano save = ciudadanoRepository.save(input);
        return save.getCiudadanoId();
    }

    public Ciudadano consultarCiudadano(Long id) throws Exception {
        return ciudadanoRepository.findById(id).orElseThrow(
                () -> new Exception("ID no encontrada")
        );
    }

    public Ciudadano buscarPorDni(String dni) throws Exception {
        Ciudadano save = null;
        List<Ciudadano> allCiudadanos = ciudadanoRepository.findAll();
        for (Ciudadano ciudadano: allCiudadanos) {
            if (ciudadano.getDni().equals(dni)) {
                save = ciudadano;
                break;
            }
        }
        if (save != null) {
            return save;
        } else {
            throw new Exception("Ciudadano no encontrado");
        }
    }

}
