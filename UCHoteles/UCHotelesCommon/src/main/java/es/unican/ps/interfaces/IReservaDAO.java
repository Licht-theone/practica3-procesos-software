package es.unican.ps.interfaces;

import java.util.List;

import es.unican.ps.clases.Reserva;
import jakarta.ejb.Local;

/**
 * Interfaz que define los metodos que implementara la DAO de reservas
 * @author aaron
 */
@Local
public interface IReservaDAO {
	
	/**
	 * Metodo que persiste una reserva a la bbdd
	 * @param r el objeto reserva
	 * @return true si se persiste false en caso contrario
	 */
	public boolean crearReserva(Reserva r);
	
	/**
	 * Metodo que recupera una reserva de la bbdd
	 * @param id id de la reserva
	 * @return la reserva recuperada o null si no existe
	 */
	public Reserva verReserva(long id);
	
	/**
	 * Metodo que borra una reserva de la bbdd
	 * @param id id de la reserva
	 * @return la reserva borrada o null si no se ha borrado
	 */
	public Reserva borrarReserva(long id);
	
	/**
	 * Metodo que modifica una reserva
	 * @param id id de la reserva a modificar
	 * @param modificada la reserva modificada
	 * @return la reserva modificada si se persiste, la original si no se persiste
	 */
	public Reserva modificarReserva(long id, Reserva modificada);
	
	/**
	 * Metodo que retorna todas las reservas
	 * @return la lista de reservas
	 */
	public List<Reserva> listarReservas();
	
	/**
	 * Metodo que lista la reservas asociadas a un hotel
	 * @param hotel el nombre del hotel
	 * @return la lista de reservas de ese hotel
	 */
	public List<Reserva> listarReservasHotel(String hotel);
}
