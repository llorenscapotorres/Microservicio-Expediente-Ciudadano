package expediente.models.controllers;

import expediente.models.dto.ExpedienteRequest;
import expediente.models.dto.ExpedienteResponse;
import expediente.models.entities.Expediente;
import expediente.models.entities.ExpedienteCiudadano;
import expediente.models.services.ExpedienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expediente")
public class ExpedienteRestController {

    @Autowired
    ExpedienteServiceImpl expedienteService;

    @GetMapping("/listar")
    public List<ExpedienteResponse> get() throws Exception {
        return expedienteService.findAll();
    }

    @GetMapping
    public ExpedienteResponse getById(@RequestParam("id") Long id) throws Exception{
        return expedienteService.findById(id);
    }

    @PostMapping("/subir")
    public ExpedienteResponse post(@RequestBody ExpedienteRequest input) throws Exception{
        return expedienteService.post(input);
    }

    @PutMapping("/actualizar")
    public ExpedienteResponse put(@RequestParam("id") Long id, @RequestBody ExpedienteRequest input) throws Exception{
        return expedienteService.put(id, input);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) throws Exception {
        return expedienteService.delete(id);
    }

    @GetMapping("/buscarPorTipoPresentacion")
    public List<ExpedienteResponse> getByTipoPresentacion(@RequestParam("tipoPresentacion") Integer tipoPresentacion) throws Exception {
        return expedienteService.findByTipoPresentacion(tipoPresentacion);
    }

    @GetMapping("/buscarPorDni")
    public List<Expediente> buscarPorDni(@RequestParam("dni") String dni) throws Exception {
        return expedienteService.ExpedientePorDniDeCiudadano(dni);
    }

    @GetMapping("/listarExpedienteCiudadano")
    public List<ExpedienteCiudadano> getAllExpedienteCiudadano() {
        return expedienteService.listAllExpedienteCiudadano();
    }

    @PostMapping("/subirExpedienteCiudadano")
    public ExpedienteCiudadano postExpedienteCiudadano(@RequestBody Expediente expediente) throws Exception {
        return expedienteService.postExpedienteCiudadano(expediente);
    }

}
