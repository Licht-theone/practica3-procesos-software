package es.unican.ps.gerente;

import java.util.List;

import es.unican.ps.clases.Reserva;
import es.unican.ps.interfaces.IGestionReservasGerente;
import es.unican.ps.interfaces.IReservaDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class GestionReservasGerente implements IGestionReservasGerente {

	@EJB
	private IReservaDAO reservaDAO;
	
	@Override
	public List<Reserva> consultarReservasHotel(String hotel) {
		return reservaDAO.listarReservasHotel(hotel);
	}

}
