package es.unican.ps.web;

import es.unican.ps.clases.Reserva;
import es.unican.ps.interfaces.IGestionReservasUsuario;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Backing Bean para consultar y cancelar reservas existentes de un usuario.
 */
@Named("gestionReservasBean")
@RequestScoped
public class GestionReservasBean {

    @EJB
    private IGestionReservasUsuario gestionReservas;

    private Long idReserva;
    private String dni;
    private Reserva reservaEncontrada;

    public GestionReservasBean() {
    }

    public String consultarReserva() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        reservaEncontrada = null;

        try {
            Reserva r = gestionReservas.consultarReserva(idReserva, dni);
            if (r == null) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No existe reserva o no corresponde al DNI indicado",
                        "No existe reserva o no corresponde al DNI indicado"));
                return null;
            }
            reservaEncontrada = r;
            return null;
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error en acceso a datos", "Error en acceso a datos"));
            return null;
        }
    }

    public String cancelarReserva() {
        FacesContext ctx = FacesContext.getCurrentInstance();

        try {
            Reserva existente = gestionReservas.consultarReserva(idReserva, dni);
            if (existente == null) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No existe reserva o no corresponde al DNI indicado",
                        "No existe reserva o no corresponde al DNI indicado"));
                return null;
            }

            Reserva cancelada = gestionReservas.cancelarReserva(idReserva, dni);
            if (cancelada == null) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Límite de cancelación de reserva sobrepasado",
                        "Límite de cancelación de reserva sobrepasado"));
                reservaEncontrada = existente;
                return null;
            }

            reservaEncontrada = null;
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Reserva " + cancelada.getId() + " cancelada correctamente",
                    "Reserva " + cancelada.getId() + " cancelada correctamente"));
            return null;
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error en acceso a datos", "Error en acceso a datos"));
            return null;
        }
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Reserva getReservaEncontrada() {
        return reservaEncontrada;
    }

    public void setReservaEncontrada(Reserva reservaEncontrada) {
        this.reservaEncontrada = reservaEncontrada;
    }
}
