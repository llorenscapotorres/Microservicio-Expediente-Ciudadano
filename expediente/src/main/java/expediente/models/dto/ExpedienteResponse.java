package expediente.models.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ExpedienteResponse {

    private Long expedienteId;
    private Integer tipoPresentacion;
    private String notas;
    private Integer ciudadanoId;
    private Date createAt;

}
