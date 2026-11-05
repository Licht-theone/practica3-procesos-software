package es.unican.ps.gerente;

import java.util.List;

import es.unican.ps.clases.Reserva;
import es.unican.ps.interfaces.IGestionReservasGerente;
import jakarta.ejb.Stateless;

@Stateless
public class GestionReservasGerente implements IGestionReservasGerente{

	@Override
	public List<Reserva> consultarReservasHotel(String hotel) {
		// TODO Auto-generated method stub
		return null;
	}

}
