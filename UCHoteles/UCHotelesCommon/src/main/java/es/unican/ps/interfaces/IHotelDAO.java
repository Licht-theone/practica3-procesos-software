package es.unican.ps.interfaces;

import java.util.List;

import es.unican.ps.clases.Hotel;
import jakarta.ejb.Local;

/**
 * Interfaz que define los metodos de la clase DAO para los objetos hotel
 * @author aaron
 */
@Local
public interface IHotelDAO {
	
	/**
	 * Metodo que persiste un objeto hotel a la bbdd
	 * @param h el objeto hotel
	 * @return true si se persiste, false en caso contrario
	 */
	public boolean crearHotel(Hotel h);
	
	/**
	 * Metodo que recupera un hotel de la bbdd con el nombre
	 * @param nombre el nombre del hotel
	 * @return el objeto hotel recuperado o null si no se recupera
	 */
	public Hotel verHotel(String nombre);
	
	/**
	 * Metodo que busca los hoteles que tengan una misma localidad
	 * @param localidad la localidad
	 * @return la lista de hoteles de esa localidad
	 */
	public List<Hotel> verHotelesLocalidad(String localidad);
	
	/**
	 * Metodo que borra un hotel de la bbdd
	 * @param id el id del hotel
	 * @return el hotel borrado o null si no existe
	 */
	public Hotel borrarHotel(long id);
	
	/**
	 * Metodo que sirve para modificar cualquier detalle del hotel (nombre, localidad, habitaciones etc)
	 * @param modificado el hotel modificado
	 * @return el hotel modificado si se consigue persistir la modificacion o el hotel sin modificar si no se persiste
	 */
	public Hotel modificarHotel(Hotel modificado);
	
	/**
	 * Metodo que lista todos los hoteles
	 * @return lista de hoteles
	 */
	public List<Hotel> listarHoteles();
	
	/**
	 * Metodo que busca un hotel por id
	 * @param id id del hotel
	 * @return el hotel recuperado o null si no existe
	 */
	public Hotel verHotelId(long id);
}
