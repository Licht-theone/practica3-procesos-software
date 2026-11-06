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
import es.unican.ps.utils.GestorBloqueos;
import jakarta.ejb.EJB;
import jakarta.ejb.Remove;
import jakarta.ejb.Stateful;

@Stateful
public class GestionReservasUsuario implements IGestionReservasUsuario {

	@EJB
	private IHotelDAO hotelDAO;
	
	@EJB
	private IReservaDAO reservaDAO;
	
	@EJB
	private GestorBloqueos gestorBloqueos;
	
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
		if (r == null || !r.getCliente().getDni().equals(dni)) {
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
		if (diasParaEntrada >= 2) {
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
		
		for (Habitacion categoria: seleccionado.getHabitaciones()) {
			int ocupadasBD = calcularOcupadasTipo(categoria.getTipo());
			int ocupadasPorBloqueos = gestorBloqueos.consultarHabitacionesBloqueadas(categoria);
			int libres = categoria.getDisponibles() - (ocupadasBD + ocupadasPorBloqueos);
			
			if (libres > 0) {
				Habitacion disponible = new Habitacion(); //objeto para devolverlo en la lista
				disponible.setTipo(categoria.getTipo());
				disponible.setPrecioPorNoche(categoria.getPrecioPorNoche());
				disponible.setDisponibles(libres);
				disponibles.add(disponible);
			}
		}
		return disponibles;
	}

	//Stateful
	@Override
	public boolean bloquearHabitaciones(long idHotel, TipoHabitacion tipo, int numHabitaciones) {
		if (seleccionado == null) return false;
		Habitacion hReal = null;
		for (Habitacion h: seleccionado.getHabitaciones()) {
			if (h.getTipo() == tipo) {
				hReal = h;
				break;
			}
		}
		
		if (hReal != null) {
			ReservaTipoHabitacion linea = new ReservaTipoHabitacion();
			linea.setHabitacion(hReal);
			linea.setNumHabitaciones(numHabitaciones);
			habitacionesBloqueadas.add(linea);
			gestorBloqueos.registrarBloqueo(linea);
			return true;
		}
		return false;
	}

	//Stateful
	@Remove
	@Override
	public Reserva confirmarReserva(DatosCliente cliente, DatosPago pago) {
		if (seleccionado == null || habitacionesBloqueadas.isEmpty()) {
			return null;
		}
		Reserva reserva = new Reserva();
		reserva.setHotel(seleccionado);
		reserva.setFechaEntrada(entrada);
		reserva.setFechaSalida(salida);
		reserva.setCliente(cliente);
		reserva.setDatosPago(pago);
		
		
		for (ReservaTipoHabitacion linea: habitacionesBloqueadas) {
			reserva.addLineaReserva(linea);
		}
		
		reservaDAO.crearReserva(reserva);
		
		for (ReservaTipoHabitacion linea: habitacionesBloqueadas) {
			gestorBloqueos.eliminaBloqueoManual(linea);
		}
		
		return reserva;
	}

	//Stateful
	@Override
	public boolean liberarHabitaciones(long idHotel, TipoHabitacion tipo, int cantidad) {
		ReservaTipoHabitacion lineaABorrar = null;
		
		for (ReservaTipoHabitacion linea: habitacionesBloqueadas) {
			if (linea.getHabitacion().getTipo() == tipo && linea.getNumHabitaciones() == cantidad 
					&& linea.getHabitacion().getHotel().getId() == idHotel) {
				lineaABorrar = linea;
				break;
			}
		}
		
		if (lineaABorrar != null) {
			habitacionesBloqueadas.remove(lineaABorrar);
			gestorBloqueos.eliminaBloqueoManual(lineaABorrar);
			return true;
		}
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
