package es.unican.ps.dao;

import java.util.List;

import es.unican.ps.clases.Reserva;
import es.unican.ps.interfaces.IReservaDAO;
import jakarta.ejb.Stateless;

@Stateless
public class ReservaDAO implements IReservaDAO{

	@Override
	public boolean crearReserva(Reserva r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Reserva verReserva(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva borrarReserva(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva modificarReserva(long id, Reserva modificada) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reserva> listarReservas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reserva> listarReservasHotel(String hotel) {
		// TODO Auto-generated method stub
		return null;
	}

}
