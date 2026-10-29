package es.unican.ps.interfaces;

import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.Hotel;
import es.unican.ps.enums.TipoHabitacion;
import jakarta.ejb.Local;

@Local
public interface IGestionHoteles {
	public Hotel consultarHotel(String hotel);
	public boolean eliminarTipoHabitacionEnHotel(Hotel hotel, TipoHabitacion t);
	public boolean modificarHabitaciones(Hotel hotel);
	public boolean anhadirTipoHabitacionEnHotel(Hotel h, Habitacion habitacion);
}
