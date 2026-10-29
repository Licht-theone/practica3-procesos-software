package es.unican.ps.interfaces;

import java.util.List;

import es.unican.ps.clases.Reserva;
import jakarta.ejb.Local;

@Local
public interface IGestionReservasGerente {
	public List<Reserva> consultarReservasHotel(String hotel);
}
