package es.unican.ps.dao;

import java.util.List;

import es.unican.ps.clases.Hotel;
import es.unican.ps.interfaces.IHotelDAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class HotelDAO implements IHotelDAO {
	
	@PersistenceContext(unitName = "UCHotelesPU")
	private EntityManager em;

	@Override
	public boolean crearHotel(Hotel h) {
		try {
			em.persist(h);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Hotel verHotel(String nombre) {
		TypedQuery<Hotel> query = em.createQuery("SELECT h FROM Hotel h WHERE h.nombre = :nombre", Hotel.class);
		query.setParameter("nombre", nombre);
		List<Hotel> resultados = query.getResultList();
		return resultados.isEmpty() ? null : resultados.get(0);
	}

	@Override
	public List<Hotel> verHotelesLocalidad(String localidad) {
		TypedQuery<Hotel> query = em.createQuery("SELECT h FROM Hotel h WHERE h.localidad = :localidad", Hotel.class);
        query.setParameter("localidad", localidad);
        return query.getResultList();
	}

	@Override
	public Hotel borrarHotel(long id) {
		Hotel h = em.find(Hotel.class, id);
        if (h != null) {
            em.remove(h);
        }
        return h;
	}

	@Override
	public Hotel modificarHotel(Hotel modificado) {
		return em.merge(modificado);
	}

	@Override
	public List<Hotel> listarHoteles() {
		return em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
	}

	@Override
	public Hotel verHotelId(long id) {
		return em.find(Hotel.class, id);
	}

}
