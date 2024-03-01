package expediente.models.common;

import expediente.models.dto.ExpedienteRequest;
import expediente.models.entities.Expediente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpedienteRequestMapper {
    ExpedienteRequestMapper INSTANCE = Mappers.getMapper(ExpedienteRequestMapper.class);

    @Mapping(target = "notas", source = "expedienteNotas")
    public Expediente expedienteRequestToExpediente(ExpedienteRequest source);
    public List<Expediente> listExpedienteRequestToListExpediente(List<ExpedienteRequest> source);
    @InheritInverseConfiguration
    public ExpedienteRequest expedienteToExpedienteRequest(Expediente source);
    @InheritInverseConfiguration
    public List<ExpedienteRequest> listExpedienteToListExpedienteRequest(List<Expediente> source);

}
