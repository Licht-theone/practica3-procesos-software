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

@Local
public interface IGestionReservasUsuario {
	public Hotel buscarHotelPorNombre(String hotel);
	public List<Hotel> buscarHotelPorLocalidad(String localidad);
	public Reserva consultarReserva(long id, String dni);
	public Reserva cancelarReserva(long id, String dni);
	public List<Habitacion> consultarDisponibilidad(long idHotel, LocalDate fechaEntrada, LocalDate fechaSalida);
	public boolean bloquearHabitaciones(long idHotel, TipoHabitacion tipo, int numHabitaciones);
	public Reserva confirmarReserva(DatosCliente cliente, DatosPago pago);
	public boolean liberarHabitaciones(long idHotel, TipoHabitacion tipo, int cantidad);
}
