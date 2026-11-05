package es.unican.ps.gerente;

import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.Hotel;
import es.unican.ps.enums.TipoHabitacion;
import es.unican.ps.interfaces.IGestionHoteles;
import jakarta.ejb.Stateless;

@Stateless
public class GestionHoteles implements IGestionHoteles{

	@Override
	public Hotel consultarHotel(String hotel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eliminarTipoHabitacionEnHotel(Hotel hotel, TipoHabitacion t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificarHabitaciones(Hotel hotel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean anhadirTipoHabitacionEnHotel(Hotel h, Habitacion habitacion) {
		// TODO Auto-generated method stub
		return false;
	}

}
