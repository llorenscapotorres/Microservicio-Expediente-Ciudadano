package expediente.models.services;

import expediente.models.common.ExpedienteRequestMapper;
import expediente.models.common.ExpedienteResponseMapper;
import expediente.models.dto.ExpedienteRequest;
import expediente.models.dto.ExpedienteResponse;
import expediente.models.entities.Ciudadano;
import expediente.models.entities.Expediente;
import expediente.models.entities.ExpedienteCiudadano;
import expediente.models.repositories.ExpedienteCiudadanoRepository;
import expediente.models.repositories.ExpedienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ExpedienteServiceImpl {

    @Autowired
    ExpedienteRepository expedienteRepository;

    @Autowired
    ExpedienteCiudadanoRepository expedienteCiudadanoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<ExpedienteResponse> findAll() throws Exception {
        List<Expediente> findAll = expedienteRepository.findAll();
        if (!findAll.isEmpty()) {
            return ExpedienteResponseMapper.INSTANCE.listExpedienteToListExpedienteResponse(findAll);
        } else {
            throw new Exception("Lista Vacía");
        }
    }

    public ExpedienteResponse findById(Long id) throws Exception{
        Expediente find = expedienteRepository.findById(id).orElseThrow(() ->
                new Exception("ID no encontrado"));
        return ExpedienteResponseMapper.INSTANCE.expedienteToExpedienteResponse(find);
    }

    public ExpedienteResponse post(ExpedienteRequest expReq) throws Exception{
        Expediente expediente = ExpedienteRequestMapper.INSTANCE.expedienteRequestToExpediente(expReq);
        if (expediente.getTipoPresentacion() != 0 && expediente.getTipoPresentacion() != 1) {
            throw new Exception("TipoPresentación requiere VALORES 0 o 1");
        }
        Expediente save = expedienteRepository.save(expediente);
        return ExpedienteResponseMapper.INSTANCE.expedienteToExpedienteResponse(save);
    }

    public ExpedienteResponse put(Long id, ExpedienteRequest expReq) throws Exception {
        Expediente input = ExpedienteRequestMapper.INSTANCE.expedienteRequestToExpediente(expReq);
        if (input.getTipoPresentacion() != 0 && input.getTipoPresentacion() != 1) {
            throw new Exception("TipoPresentación requiere VALORES 0 o 1");
        }
        try {
            Expediente findedExpediente = expedienteRepository.findById(id).orElse(null);
            if (findedExpediente != null){
                findedExpediente.setTipoPresentacion(input.getTipoPresentacion());
                findedExpediente.setNotas(input.getNotas());
                findedExpediente.setCiudadanoId(input.getCiudadanoId());
            }
            return ExpedienteResponseMapper.INSTANCE.
                    expedienteToExpedienteResponse(expedienteRepository.
                            save(Objects.requireNonNull(findedExpediente)));
        } catch (NullPointerException e){
            NullPointerException exception = new NullPointerException("ERROR con ID");
            throw exception;
        }
    }

    public ResponseEntity<?> delete(Long id) throws Exception {
        Expediente deletedExpediente = expedienteRepository.findById(id).
                orElseThrow(() -> new Exception("ID no encontrado"));
        expedienteRepository.delete(deletedExpediente);
        return ResponseEntity.ok().build();
    }

    /*public List<Expediente> findByTipoPresentacion(Integer tipoPresentacion) throws Exception {
        if (tipoPresentacion != 0 && tipoPresentacion != 1) {
            throw new Exception("TipoPresentación requiere VALORES 0 o 1");
        }
        List<Expediente> expedientesMod = new ArrayList<>();
        for (Expediente expediente: this.findAll()) {
            if (Objects.equals(expediente.getTipoPresentacion(), tipoPresentacion)) {
                expedientesMod.add(expediente);
            }
        }
        return expedientesMod;
    }*/

    public List<ExpedienteResponse> findByTipoPresentacion(Integer tipoPresentacion) throws Exception {
        if (tipoPresentacion != 0 && tipoPresentacion != 1) {
            throw new Exception("TipoPresentación requiere VALORES 0 o 1");
        }
        List<Expediente> expedientes = new ArrayList<>();
        expedienteRepository.findAll().forEach(expediente ->
            {
                if (expediente.getTipoPresentacion().equals(tipoPresentacion)) {
                    expedientes.add(expediente);
                }
            });
        return ExpedienteResponseMapper.INSTANCE.
                listExpedienteToListExpedienteResponse(expedientes);
    }

    /*public List<Expediente> findByTipoPresentacion(Integer tipoPresentacion) throws Exception {
        if (tipoPresentacion != 0 && tipoPresentacion != 1) {
            throw new Exception("TipoPresentación requiere VALORES 0 o 1");
        }
        return expedienteRepository.findByTipoPresentacion(tipoPresentacion);
    }*/

    public List<Expediente> ExpedientePorDniDeCiudadano(String dni) throws Exception {

        //buscamor primero el ciudadano por el dni
        String url = "http://localhost:8082/ciudadanos/buscarPorDni?dni={dni}";
        /*Map<String, String> params = new HashMap<>();
        params.put("dni", dni);*/
        Ciudadano ciudadano = restTemplate.getForObject(url, Ciudadano.class, dni);

        //el ciudadano comparte un id con Expediente (ciudadanoId)
        List<Expediente> findList = new ArrayList<>();
        List<Expediente> allExpedientes = expedienteRepository.findAll();
        for (Expediente expediente: allExpedientes) {
            if (expediente.getCiudadanoId().compareTo(ciudadano.getCiudadanoId().intValue()) == 0) {
                findList.add(expediente);
            }
        }
        if (!findList.isEmpty()) {
            return findList;
        } else {
            throw new Exception("No existe ningún expediente asociado con un ciudadano con el DNI indicado");
        }
    }

    public List<ExpedienteCiudadano> listAllExpedienteCiudadano() {
        return expedienteCiudadanoRepository.findAll();
    }

    public ExpedienteCiudadano postExpedienteCiudadano(Expediente expediente) throws Exception {
        if (expediente.getTipoPresentacion() != 0 && expediente.getTipoPresentacion() != 1) {
            throw new Exception("Error con tipoPresentacion");
        }

        //encontremos el ciudadano relacionado mediante el ciudadanoId del Expediente
        String url = "http://localhost:8082/ciudadanos/consultarCiudadano?id={id}";
        Map<String, Integer> params = new HashMap<>();
        params.put("id", expediente.getCiudadanoId());
        Ciudadano ciudadano = restTemplate.getForObject(url, Ciudadano.class, params);

        //construimos el objeto expedienteCiudadano
        ExpedienteCiudadano expedienteCiudadano = new ExpedienteCiudadano();
        expedienteCiudadano.setNombre(ciudadano.getNombre());
        expedienteCiudadano.setPrimerApellido(ciudadano.getPrimerApellido());
        expedienteCiudadano.setSegundoApellido(ciudadano.getSegundoApellido());
        expedienteCiudadano.setDni(ciudadano.getDni());
        expedienteCiudadano.setEmail(ciudadano.getEmail());
        expedienteCiudadano.setNotas(expediente.getNotas());
        expedienteCiudadano.setTipoPresentacion(expediente.getTipoPresentacion());

        return expedienteCiudadanoRepository.save(expedienteCiudadano);
    }

}
