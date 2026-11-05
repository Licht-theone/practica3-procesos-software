package es.unican.ps.interfaces;

import java.time.LocalDate;
import java.util.List;

import es.unican.ps.clases.DatosCliente;
import es.unican.ps.clases.DatosPago;
import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.Hotel;
import es.unican.ps.clases.Reserva;
import es.unican.ps.enums.TipoHabitacion;
import jakarta.ejb.Local;

/**
 * Interfaz que define los metodos que implementara la clase gestionreservasusuario
 * @author aaron
 */
@Local
public interface IGestionReservasUsuario {
	/**
	 * Metodo que permite buscar un hotel por nombre
	 * @param hotel el nombre del hotel
	 * @return el objeto hotel encontrado o null si no existe
	 */
	public Hotel buscarHotelPorNombre(String hotel);
	
	/**
	 * Metodo que busca los hoteles de una localidad
	 * @param localidad el nombre de la localidad
	 * @return una lista de hoteles
	 */
	public List<Hotel> buscarHotelPorLocalidad(String localidad);
	
	/**
	 * Metodo que permite conocer los detalles de una reserva de un cliente
	 * @param id el id de la reserva
	 * @param dni el dni del cliente
	 * @return el objeto reserva o null si no existe
	 */
	public Reserva consultarReserva(long id, String dni);
	
	/**
	 * Metodo que permite anular una reserva
	 * @param id el id de la reserva a anular
	 * @param dni el dni del cliente
	 * @return el objeto reserva que se ha eliminado o null si no existe
	 */
	public Reserva cancelarReserva(long id, String dni);
	
	/**
	 * Meotodo que devuelve la lista de habitaciones disponibles para un hotel y fechas determinadas
	 * @param idHotel el id del hotel
	 * @param fechaEntrada la fecha de entrada
	 * @param fechaSalida la fecha de salida
	 * @return la lista de habitaciones disponibles
	 */
	public List<Habitacion> consultarDisponibilidad(long idHotel, LocalDate fechaEntrada, LocalDate fechaSalida);
	
	/**
	 * Metodo que bloquea temporalmente habitaciones al hacer una reserva (10 mins)
	 * @param idHotel id del hotel
	 * @param tipo el tipo de habitacion a bloquear
	 * @param numHabitaciones el numero de habitaciones a bloquear
	 * @return true si se bloquean, false en caso contrario
	 */
	public boolean bloquearHabitaciones(long idHotel, TipoHabitacion tipo, int numHabitaciones);
	
	/**
	 * Metodo que finaliza el proceso de reserva
	 * @param cliente datos del cliente
	 * @param pago datos del pago
	 * @return el objeto reserva generado o null si falla
	 */
	public Reserva confirmarReserva(DatosCliente cliente, DatosPago pago);
	
	/**
	 * Metodo que libera las habitaciones en caso de expirar el tiempo
	 * @param idHotel el hotel que del que liberar
	 * @param tipo el tipo de habitacion a liberar
	 * @param cantidad cuantas habitaciones a liberar
	 * @return true si se liberan, false en caso contrario
	 */
	public boolean liberarHabitaciones(long idHotel, TipoHabitacion tipo, int cantidad);
}
