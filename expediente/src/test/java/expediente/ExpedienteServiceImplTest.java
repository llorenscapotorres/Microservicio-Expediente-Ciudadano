package expediente;

import expediente.models.dto.ExpedienteRequest;
import expediente.models.dto.ExpedienteResponse;
import expediente.models.entities.Expediente;
import expediente.models.repositories.ExpedienteRepository;
import expediente.models.services.ExpedienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpedienteServiceImplTest {

	// Como ExpedienteServiceImpl utiliza el ExpedienteRepository, y más en concreto
	// también lo utiliza la funcion findAll() de la que queremos hacer el test, debemos hacer
	// lo siguiente:

	@Mock
	ExpedienteRepository expedienteRepository;

    //Además, queremos hacer tests sobre la clase del servicio:

	@InjectMocks
	ExpedienteServiceImpl expedienteService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAllOK() throws Exception {
		Mockito.when(expedienteRepository.findAll())
				.thenReturn(mockAdminstrationExpedienteList());

		List<ExpedienteResponse> result = expedienteService.findAll();
		assertEquals(1L, result.get(0).getExpedienteId());
	}

	@Test
	void testFindAllKO() {
		Mockito.when(expedienteRepository.findAll()).thenReturn(Collections.emptyList());
		Exception exception = assertThrows(Exception.class, () -> expedienteService.findAll());
		assertEquals("Lista Vacía", exception.getMessage());
	}

	@Test
	void testFindByIdOK() throws Exception {
		Mockito.when(expedienteRepository.findById(anyLong()))
				.thenReturn(Optional.of(mockAdministrationExpediente()));

		ExpedienteResponse result = expedienteService.findById(1L);
		assertNotNull(result);
		assertEquals(1L, result.getExpedienteId());
	}
	
	@Test
	void testFindByIdKO() {
		Mockito.when(expedienteRepository.findById(anyLong())).thenReturn(Optional.empty());
		Exception exception = assertThrows(Exception.class,
				() -> expedienteService.findById(anyLong()));
		assertEquals("ID no encontrado", exception.getMessage());
	}

	@Test
	void testPostSaveOK() throws Exception {
		Mockito.when(expedienteRepository.save(any(Expediente.class)))
				.thenReturn(mockAdministrationExpediente());

		ExpedienteResponse result = expedienteService.post(mockAdministrationExpedienteRequest());
		assertEquals(1L, result.getExpedienteId());
		assertEquals(0, result.getTipoPresentacion());
		assertEquals("String", result.getNotas());
		assertEquals(100, result.getCiudadanoId());
		//assertEquals(new Date(), result.getCreateAt());

		verify(expedienteRepository).save(any(Expediente.class));
	}

	@Test
	void testPostSaveKO() {
		Exception exception = assertThrows(Exception.class,
				() -> expedienteService.post(mockAdministrationExpReqFalloPresentacion()));
		assertEquals("TipoPresentación requiere VALORES 0 o 1",
				exception.getMessage());
	}

	@Test
	void testPutOK() throws Exception {
		Mockito.when(expedienteRepository.findById(anyLong()))
				.thenReturn(Optional.of(mockAdministrationExpediente()));
		Mockito.when(expedienteRepository.save(any(Expediente.class)))
				.thenReturn(mockAdministrationExpediente());

		ExpedienteResponse result = expedienteService
				.put(anyLong(), mockAdministrationExpedienteRequest());
		assertEquals(1L, result.getExpedienteId());
		assertEquals("String", result.getNotas());

		verify(expedienteRepository).findById(anyLong());
		verify(expedienteRepository).save(any(Expediente.class));
	}

	@Test
	void testPutKO() {
		Exception exception = assertThrows(Exception.class,
				() -> expedienteService.put(anyLong(), mockAdministrationExpReqFalloPresentacion()));
		assertEquals("TipoPresentación requiere VALORES 0 o 1", exception.getMessage());

		NullPointerException nullPointerException = assertThrows(NullPointerException.class,
				() -> expedienteService.put(99L, mockAdministrationExpedienteRequest()));
		assertEquals("ERROR con ID", nullPointerException.getMessage());
	}

	private List<Expediente> mockAdminstrationExpedienteList() {
		List<Expediente> expedienteList = new ArrayList<>();
		Expediente expediente = new Expediente();
		expediente.setId(1L);
		expediente.setTipoPresentacion(0);
		expediente.setNotas("String");
		expediente.setCiudadanoId(100);
		expediente.setCreateAt(new Date());
		expedienteList.add(expediente);
		return expedienteList;
	}

	private Expediente mockAdministrationExpediente() {
		Expediente exppediente = new Expediente();
		exppediente.setId(1L);
		exppediente.setTipoPresentacion(0);
		exppediente.setNotas("String");
		exppediente.setCiudadanoId(100);
		exppediente.setCreateAt(new Date());
		return exppediente;
	}

	private ExpedienteRequest mockAdministrationExpedienteRequest() {
		ExpedienteRequest expedienteRequest = new ExpedienteRequest();
		expedienteRequest.setTipoPresentacion(0);
		expedienteRequest.setExpedienteNotas("String");
		expedienteRequest.setCiudadanoId(100);
		expedienteRequest.setCreateAt(new Date());
		return expedienteRequest;
	}

	private ExpedienteRequest mockAdministrationExpReqFalloPresentacion() {
		ExpedienteRequest expedienteRequest = new ExpedienteRequest();
		expedienteRequest.setTipoPresentacion(2);
		expedienteRequest.setExpedienteNotas("String");
		expedienteRequest.setCiudadanoId(100);
		expedienteRequest.setCreateAt(new Date());
		return expedienteRequest;
	}

}
