package com.ciudadanos.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ciudadano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ciudadanoId;
    private String nombre;
    @Column(name = "primer_apellido")
    private String primerApellido;
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    private String dni;
    private String email;

}
