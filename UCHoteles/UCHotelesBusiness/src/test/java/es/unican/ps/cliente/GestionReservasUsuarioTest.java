package es.unican.ps.cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.unican.ps.clases.DatosCliente;
import es.unican.ps.clases.Reserva;
import es.unican.ps.interfaces.IHotelDAO;
import es.unican.ps.interfaces.IReservaDAO;
import es.unican.ps.utils.GestorBloqueos;

@ExtendWith(MockitoExtension.class)
public class GestionReservasUsuarioTest {

	@Mock
	private IReservaDAO reservaDAOMock;
	
	@Mock
	private IHotelDAO hotelDAOMock;
	
	@Mock
	private GestorBloqueos gestorMock;
	
	@InjectMocks
	private GestionReservasUsuario sut;
	
	@Test
	public void testConsultarReserva_PU01() {
		long id = 1L;
		String dni = "12345678A";
		DatosCliente datos = new DatosCliente(dni, null, null, null, null);
		Reserva reservaMock = new Reserva();
		reservaMock.setCliente(datos);
		
		when(reservaDAOMock.verReserva(id)).thenReturn(reservaMock);
		
		Reserva resultado = sut.consultarReserva(id, dni);
		assertNotNull(resultado, "la reserva no deberia ser null");
		assertEquals(dni, resultado.getCliente().getDni());
	}
	
	@Test
	public void testConsultarReserva_PU02() {
		long id = 1L;
		String dniCorrecto = "87654321B";
		String dniMalo = "12345678A";
		DatosCliente datos = new DatosCliente(dniCorrecto, null, null, null, null);
		Reserva reservaMock = new Reserva();
		reservaMock.setCliente(datos);
		when(reservaDAOMock.verReserva(id)).thenReturn(reservaMock);
		
		Reserva resultado = sut.consultarReserva(id, dniMalo);
		
		assertNull(resultado, "Deberia ser nulo al no coincidir el dni");
	}
	
	@Test
	public void testConsultarReserva_PU03() {
		long id = 67;
		String dniCorrecto = "87654321B";
		when(reservaDAOMock.verReserva(id)).thenReturn(null);
		
		Reserva resultado = sut.consultarReserva(id, dniCorrecto);
		assertNull(resultado, "La reserva deberia ser null por id invalido");
	}
	
	@Test
	public void testConsultarReserva_PU04() {
		long id = 1L;
		Reserva reservaMock = new Reserva();
		when(reservaDAOMock.verReserva(id)).thenReturn(reservaMock);
		
		Reserva resultado = sut.consultarReserva(id, null);
		assertNull(resultado, "El dni es nulo, la reserva deberia ser nula");
	}
}
