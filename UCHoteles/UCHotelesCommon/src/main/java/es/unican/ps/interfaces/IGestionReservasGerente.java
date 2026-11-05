package es.unican.ps.interfaces;

import java.util.List;

import es.unican.ps.clases.Reserva;
import jakarta.ejb.Local;

/**
 * Interfaz que define los metodos que implementara la clase gestionreservasgerente
 * @author aaron
 */
@Local
public interface IGestionReservasGerente {
	
	/**
	 * Metodo que lista las reservas asociadas a un hotel
	 * @param hotel el nombre del hotel
	 * @return una lista de reservas con las reservas encontradas
	 */
	public List<Reserva> consultarReservasHotel(String hotel);
}
