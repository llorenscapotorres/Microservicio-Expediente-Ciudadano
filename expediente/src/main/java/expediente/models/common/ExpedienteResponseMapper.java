package expediente.models.common;

import expediente.models.dto.ExpedienteResponse;
import expediente.models.entities.Expediente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpedienteResponseMapper {
    ExpedienteResponseMapper INSTANCE = Mappers.getMapper(ExpedienteResponseMapper.class);
    @Mapping(target = "id", source = "expedienteId")
    public Expediente expedienteResponseToExpediente(ExpedienteResponse source);
    public List<Expediente> listExpedienteResponseToListExpediente(List<ExpedienteResponse> source);
    @InheritInverseConfiguration
    public ExpedienteResponse expedienteToExpedienteResponse(Expediente source);
    @InheritInverseConfiguration
    public List<ExpedienteResponse> listExpedienteToListExpedienteResponse(List<Expediente> source);

}
