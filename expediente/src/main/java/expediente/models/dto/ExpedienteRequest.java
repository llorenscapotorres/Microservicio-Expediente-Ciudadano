package expediente.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ExpedienteRequest {

    private Integer tipoPresentacion;
    private String expedienteNotas;
    private Integer ciudadanoId;
    private Date createAt;

}
