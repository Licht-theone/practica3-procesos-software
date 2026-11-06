package es.unican.ps.cliente;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import es.unican.ps.clases.DatosCliente;
import es.unican.ps.clases.DatosPago;
import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.Hotel;
import es.unican.ps.clases.Reserva;
import es.unican.ps.clases.ReservaTipoHabitacion;
import es.unican.ps.enums.TipoHabitacion;
import es.unican.ps.interfaces.IGestionReservasUsuario;
import es.unican.ps.interfaces.IHotelDAO;
import es.unican.ps.interfaces.IReservaDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;

@Stateful
public class GestionReservasUsuario implements IGestionReservasUsuario {

	@EJB
	private IHotelDAO hotelDAO;
	
	@EJB
	private IReservaDAO reservaDAO;
	
	//atributos para el caso reserva que mantiene un estado
	private Hotel seleccionado;
	private LocalDate entrada;
	private LocalDate salida;
	private List<ReservaTipoHabitacion> habitacionesBloqueadas = new ArrayList<>();
	
	//Stateless
	@Override
	public Hotel buscarHotelPorNombre(String hotel) {
		return hotelDAO.verHotel(hotel);
	}
	
	//Stateless
	@Override
	public List<Hotel> buscarHotelPorLocalidad(String localidad) {
		return hotelDAO.verHotelesLocalidad(localidad);
	}

	//Stateless
	@Override
	public Reserva consultarReserva(long id, String dni) {
		Reserva r = reservaDAO.verReserva(id);
		if (r.getCliente().getDni() != dni) {
			return null;
		}
		return r;
	}

	//Stateless
	@Override
	public Reserva cancelarReserva(long id, String dni) {
		Reserva r = consultarReserva(id, dni);
		if (r == null) {
			return null;
		}
		long diasParaEntrada = ChronoUnit.DAYS.between(LocalDate.now(), entrada);
		if (diasParaEntrada < 2) {
			return reservaDAO.borrarReserva(id);
		}
		return null;
	}

	//Stateful
	@Override
	public List<Habitacion> consultarDisponibilidad(long idHotel, LocalDate fechaEntrada, LocalDate fechaSalida) {
		this.seleccionado = hotelDAO.verHotelId(idHotel);
		this.entrada = fechaEntrada;
		this.salida = fechaSalida;
		this.habitacionesBloqueadas.clear();
		if (seleccionado == null) {
			return new ArrayList<>();
 		}
		List<Habitacion> disponibles = new ArrayList<>();
		
		return null;
	}

	//Stateful
	@Override
	public boolean bloquearHabitaciones(long idHotel, TipoHabitacion tipo, int numHabitaciones) {
		// TODO Auto-generated method stub
		return false;
	}

	//Stateful
	@Override
	public Reserva confirmarReserva(DatosCliente cliente, DatosPago pago) {
		// TODO Auto-generated method stub
		return null;
	}

	//Stateful
	@Override
	public boolean liberarHabitaciones(long idHotel, TipoHabitacion tipo, int cantidad) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Metodo auxiliar para calcular ocupacion para un tipo de habitacion concreto en el hotel seleccionado
	 * @param t tipo habitacion
	 * @return el numero de habitaciones de ese tipo ocupadas entre la entrada y la salida (>=0)
	 */
	private int calcularOcupadasTipo(TipoHabitacion t) {
		int total = 0;
		List<Reserva> reservas = reservaDAO.listarReservasHotel(seleccionado.getNombre());
		
		for (Reserva r: reservas) {
			boolean solapa = (entrada.isBefore(r.getFechaSalida()) && salida.isAfter(r.getFechaEntrada()));
			if (solapa) {
				for (ReservaTipoHabitacion linea: r.getLineasReserva()) {
					if (linea.getHabitacion().getTipo() == t) {
						total += linea.getNumHabitaciones();
					}
				}
			}
		}
		return total;
	}

}
