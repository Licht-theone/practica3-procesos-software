package es.unican.ps.interfaces;

import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.Hotel;
import es.unican.ps.enums.TipoHabitacion;
import jakarta.ejb.Local;

/**
 * Interfaz que define los metodos que implementara la clase gestion hoteles en el modulo business
 * @author aaron
 */
@Local
public interface IGestionHoteles {
	
	/**
	 * Metodo que permite consultar un hotel con su nombre
	 * @param hotel el nombre del hotel
	 * @return el objeto de tipo hotel recuperado o null si no existe
	 */
	public Hotel consultarHotel(String hotel);
	
	/**
	 * Metodo que permite eliminar un tipo de habitacion de un hotel
	 * @param hotel el hotel del que se eliminara el tipo de habitacion
	 * @param t el tipo de habitacion a eliminar
	 * @return true si se ha ejecutado correctamente, false en caso de error
	 */
	public boolean eliminarTipoHabitacionEnHotel(Hotel hotel, TipoHabitacion t);
	
	/**
	 * Metodo que permite modificar las habitaciones de un hotel
	 * @param hotel el hotel con sus habitaciones dentro modificadas
	 * @return true si se ejecuta correctamente, false en caso contrario
	 */
	public boolean modificarHabitaciones(Hotel hotel);
	
	/**
	 * Metodo que permite añadir un tipo de habitacion al hotel
	 * @param h el hotel
	 * @param habitacion la habitacion con todos sus parametros definidos
	 * @return true si se ejecuta correctamente, false en caso contrario
	 */
	public boolean anhadirTipoHabitacionEnHotel(Hotel h, Habitacion habitacion);
}
