package es.unican.ps.dao;

import java.util.List;

import es.unican.ps.clases.Reserva;
import es.unican.ps.interfaces.IReservaDAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class ReservaDAO implements IReservaDAO {
	
	@PersistenceContext(unitName = "UCHotelesPU")
	private EntityManager em;

	@Override
	public boolean crearReserva(Reserva r) {
		try {
            em.persist(r);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

	@Override
	public Reserva verReserva(long id) {
		return em.find(Reserva.class, id);
	}

	@Override
	public Reserva borrarReserva(long id) {
		Reserva r = em.find(Reserva.class, id);
        if (r != null) {
            em.remove(r);
        }
        return r;
	}

	@Override
	public Reserva modificarReserva(long id, Reserva modificada) {
		modificada.setId(id);
        return em.merge(modificada);
	}

	@Override
	public List<Reserva> listarReservas() {
		return em.createQuery("SELECT r FROM Reserva r", Reserva.class).getResultList();
	}

	@Override
	public List<Reserva> listarReservasHotel(String hotel) {
		TypedQuery<Reserva> query = em.createQuery("SELECT r FROM Reserva r WHERE r.hotel.nombre = :nombre", Reserva.class);
	    query.setParameter("nombre", hotel);
	    return query.getResultList();
	}

}
