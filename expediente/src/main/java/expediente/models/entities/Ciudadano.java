package expediente.models.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Ciudadano {

    private Long ciudadanoId;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String dni;
    private String email;

}
