package com.ciudadanos.models.controllers;

import com.ciudadanos.models.entities.Ciudadano;
import com.ciudadanos.models.services.CiudadanoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ciudadanos")
public class CiudadanoController {

    @Autowired
    CiudadanoServiceImpl ciudadanoService;

    @GetMapping()
    public List<Ciudadano> get() {
        return ciudadanoService.listar();
    }

    @PostMapping("/altaCiudadano")
    public Long darDeAlta(@RequestBody Ciudadano input) {
        return ciudadanoService.darDeAlta(input);
    }

    @GetMapping("/consultarCiudadano")
    public Ciudadano getById(@RequestParam("id") Long id) throws Exception {
        return ciudadanoService.consultarCiudadano(id);
    }

    @GetMapping("/buscarPorDni")
    public Ciudadano getByDni(@RequestParam("dni") String dni) throws Exception {
        return ciudadanoService.buscarPorDni(dni);
    }

}
