package es.unican.ps.interfaces;

import java.util.List;

import es.unican.ps.clases.Reserva;
import jakarta.ejb.Local;

@Local
public interface IReservaDAO {
	public boolean crearReserva(Reserva r);
	public Reserva verReserva(long id);
	public Reserva borrarReserva(long id);
	public Reserva modificarReserva(long id, Reserva modificada);
	public List<Reserva> listarReservas();
	public List<Reserva> listarReservasHotel(String hotel);
}
