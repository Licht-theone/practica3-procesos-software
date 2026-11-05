package es.unican.ps.cliente;

import java.time.LocalDate;
import java.util.List;

import es.unican.ps.clases.DatosCliente;
import es.unican.ps.clases.DatosPago;
import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.Hotel;
import es.unican.ps.clases.Reserva;
import es.unican.ps.enums.TipoHabitacion;
import es.unican.ps.interfaces.IGestionReservasUsuario;

public class GestionReservasUsuario implements IGestionReservasUsuario {

	@Override
	public Hotel buscarHotelPorNombre(String hotel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hotel> buscarHotelPorLocalidad(String localidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva consultarReserva(long id, String dni) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva cancelarReserva(long id, String dni) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Habitacion> consultarDisponibilidad(long idHotel, LocalDate fechaEntrada, LocalDate fechaSalida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean bloquearHabitaciones(long idHotel, TipoHabitacion tipo, int numHabitaciones) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Reserva confirmarReserva(DatosCliente cliente, DatosPago pago) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean liberarHabitaciones(long idHotel, TipoHabitacion tipo, int cantidad) {
		// TODO Auto-generated method stub
		return false;
	}

}
