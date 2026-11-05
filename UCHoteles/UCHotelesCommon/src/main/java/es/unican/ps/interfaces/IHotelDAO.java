package es.unican.ps.interfaces;

import java.util.List;

import es.unican.ps.clases.Hotel;
import jakarta.ejb.Local;

@Local
public interface IHotelDAO {
	public boolean crearHotel(Hotel h);
	public Hotel verHotel(String nombre);
	public List<Hotel> verHotelesLocalidad(String localidad);
	public Hotel borrarHotel(long id);
	public Hotel modificarHotel(Hotel modificado);
	public List<Hotel> listarHoteles();
}
