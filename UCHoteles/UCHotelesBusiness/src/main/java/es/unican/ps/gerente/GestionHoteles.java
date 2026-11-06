package es.unican.ps.gerente;

import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.Hotel;
import es.unican.ps.enums.TipoHabitacion;
import es.unican.ps.interfaces.IGestionHoteles;
import es.unican.ps.interfaces.IHotelDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class GestionHoteles implements IGestionHoteles {

	@EJB
	private IHotelDAO hotelDAO;

	@Override
	public Hotel consultarHotel(String hotel) {
		return hotelDAO.verHotel(hotel);
	}

	@Override
	public boolean eliminarTipoHabitacionEnHotel(Hotel hotel, TipoHabitacion t) {
		Hotel hotelActualizado = consultarHotel(hotel.getNombre()); //instancia mas reciente
		if (hotelActualizado != null) {
			hotelActualizado.getHabitaciones().removeIf(hab -> hab.getTipo() == t);
			if (modificarHabitaciones(hotelActualizado)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean modificarHabitaciones(Hotel hotel) {
		Hotel res = hotelDAO.modificarHotel(hotel);
		return res != null;
	}

	@Override
	public boolean anhadirTipoHabitacionEnHotel(Hotel h, Habitacion habitacion) {
		Hotel hotelActualizado = consultarHotel(h.getNombre()); //instancia mas reciente
		if (hotelActualizado != null) {
			hotelActualizado.addHabitacion(habitacion);
			if (modificarHabitaciones(hotelActualizado)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
