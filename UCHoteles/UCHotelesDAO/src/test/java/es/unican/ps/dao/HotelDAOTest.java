package es.unican.ps.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.unican.ps.clases.Hotel;
import es.unican.ps.interfaces.IHotelDAO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

@ExtendWith(MockitoExtension.class)
public class HotelDAOTest {
	
	@Mock
	private EntityManager emMock;
	
	@InjectMocks
	private HotelDAO sut;

	@Test
	public void testCrearHotel_PU01() {
		Hotel hotel = new Hotel();
		hotel.setNombre("Bahia");
		
		boolean resultado = sut.crearHotel(hotel);
		
		assertTrue(resultado, "Deberia haberse creado el hotel");
		verify(emMock, times(1)).persist(hotel);
	}
	
	@Test
	public void testCrearHotel_PU02() {
		Hotel h = new Hotel();
		doThrow(new PersistenceException()).when(emMock).persist(h);
		boolean resultado = sut.crearHotel(h);
		assertFalse(resultado, "Deberia devolver false al no poder persistir");
		verify(emMock, times(1)).persist(h);
	}
	
	@Test
	public void testCrearHotel_PU03() {
		Hotel hotel = new Hotel();
        hotel.setNombre("Bahia");
        
        doThrow(new EntityExistsException())
            .when(emMock).persist(hotel);

        boolean resultado = sut.crearHotel(hotel);

        assertFalse(resultado, "Debería devolver false al capturar EntityExistsException");
        
        verify(emMock, times(1)).persist(hotel);
		
	}
	
	@Test
	public void testCrearHotel_PU04() {
		Hotel hotelNulo = null;
        
        doThrow(new IllegalArgumentException("No se puede persistir un objeto null"))
            .when(emMock).persist(hotelNulo);

        boolean resultado = sut.crearHotel(hotelNulo);

        assertFalse(resultado, "Debería devolver false al capturar IllegalArgumentException");

        verify(emMock, times(1)).persist(hotelNulo);
	}
}
