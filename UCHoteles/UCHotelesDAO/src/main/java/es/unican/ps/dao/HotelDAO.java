package es.unican.ps.dao;

import java.util.List;

import es.unican.ps.clases.Hotel;
import es.unican.ps.interfaces.IHotelDAO;
import jakarta.ejb.Stateless;

@Stateless
public class HotelDAO implements IHotelDAO{

	@Override
	public boolean crearHotel(Hotel h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Hotel verHotel(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hotel> verHotelesLocalidad(String localidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hotel borrarHotel(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hotel modificarHotel(Hotel modificado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hotel> listarHoteles() {
		// TODO Auto-generated method stub
		return null;
	}

}
