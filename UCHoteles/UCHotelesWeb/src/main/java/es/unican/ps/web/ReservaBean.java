package es.unican.ps.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import es.unican.ps.clases.DatosCliente;
import es.unican.ps.clases.DatosPago;
import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.Hotel;
import es.unican.ps.clases.Reserva;
import es.unican.ps.enums.TipoTarjeta;
import es.unican.ps.interfaces.IGestionReservasUsuario;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Backing Bean de sesion encargado del flujo completo de busqueda de hoteles y reserva de habitaciones.
 */
@Named("reservaBean")
@SessionScoped
public class ReservaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IGestionReservasUsuario gestionReservas;

    // Criterios de busqueda
    private String nombreHotel;
    private String localidad;

    // Resultados de busqueda y seleccion
    private List<Hotel> hotelesEncontrados = new ArrayList<>();
    private Hotel hotelSeleccionado;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    // Disponibilidad y seleccion de habitaciones
    private List<HabitacionSeleccion> habitacionesDisponibles = new ArrayList<>();
    private List<HabitacionSeleccion> habitacionesBloqueadas = new ArrayList<>();
    private double importeTotal;

    // Datos del cliente y pago
    private DatosCliente cliente = new DatosCliente();
    private DatosPago pago = new DatosPago();

    // Reserva confirmada
    private Reserva reservaConfirmada;

    public ReservaBean() {
    }

    /**
     * Clase auxiliar para vincular cada tipo de habitacion disponible con el numero de habitaciones
     * solicitadas por el usuario en la tabla de disponibilidad.
     */
    public static class HabitacionSeleccion implements Serializable {
        private static final long serialVersionUID = 1L;
        private Habitacion habitacion;
        private int numSeleccionadas;

        public HabitacionSeleccion() {
        }

        public HabitacionSeleccion(Habitacion habitacion, int numSeleccionadas) {
            this.habitacion = habitacion;
            this.numSeleccionadas = numSeleccionadas;
        }

        public Habitacion getHabitacion() {
            return habitacion;
        }

        public void setHabitacion(Habitacion habitacion) {
            this.habitacion = habitacion;
        }

        public int getNumSeleccionadas() {
            return numSeleccionadas;
        }

        public void setNumSeleccionadas(int numSeleccionadas) {
            this.numSeleccionadas = numSeleccionadas;
        }
    }

    /**
     * Busca hoteles por nombre o por localidad segun los campos introducidos.
     */
    public String buscarHoteles() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        hotelesEncontrados = new ArrayList<>();

        try {
            if (nombreHotel != null && !nombreHotel.trim().isEmpty()) {
                Hotel h = gestionReservas.buscarHotelPorNombre(nombreHotel.trim());
                if (h == null) {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Hotel no encontrado", "Hotel no encontrado"));
                    return null;
                }
                hotelesEncontrados.add(h);
                return "listaHoteles.xhtml";
            } else if (localidad != null && !localidad.trim().isEmpty()) {
                List<Hotel> lista = gestionReservas.buscarHotelPorLocalidad(localidad.trim());
                if (lista == null || lista.isEmpty()) {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No existen hoteles para esa localidad", "No existen hoteles para esa localidad"));
                    return null;
                }
                hotelesEncontrados = lista;
                return "listaHoteles.xhtml";
            } else {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Introduzca un nombre de hotel o una localidad",
                        "Introduzca un nombre de hotel o una localidad"));
                return null;
            }
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error en acceso a datos", "Error en acceso a datos"));
            return null;
        }
    }

    /**
     * Consulta la disponibilidad de habitaciones para el hotel y las fechas seleccionadas.
     */
    public String verDisponibilidad(Hotel hotel) {
        FacesContext ctx = FacesContext.getCurrentInstance();

        if (fechaEntrada == null || fechaSalida == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe introducir las fechas de entrada y salida",
                    "Debe introducir las fechas de entrada y salida"));
            return null;
        }

        if (!fechaSalida.isAfter(fechaEntrada)) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La fecha de salida debe ser posterior a la fecha de entrada",
                    "La fecha de salida debe ser posterior a la fecha de entrada"));
            return null;
        }

        try {
            List<Habitacion> disponibles = gestionReservas.consultarDisponibilidad(
                    hotel.getId(), fechaEntrada, fechaSalida);
            if (disponibles == null || disponibles.isEmpty()) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No hay disponibilidad, pruebe otras fechas",
                        "No hay disponibilidad, pruebe otras fechas"));
                return null;
            }

            this.hotelSeleccionado = hotel;
            this.habitacionesDisponibles = new ArrayList<>();
            for (Habitacion h : disponibles) {
                this.habitacionesDisponibles.add(new HabitacionSeleccion(h, 0));
            }
            return "disponibilidad.xhtml";
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error en acceso a datos", "Error en acceso a datos"));
            return null;
        }
    }

    /**
     * Comprueba disponibilidad, bloquea temporalmente las habitaciones seleccionadas (10 minutos)
     * y calcula el importe total previo a la confirmacion.
     */
    public String reservarHabitaciones() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        int totalHabitaciones = 0;

        for (HabitacionSeleccion item : habitacionesDisponibles) {
            if (item.getNumSeleccionadas() < 0) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "El numero de habitaciones no puede ser negativo",
                        "El numero de habitaciones no puede ser negativo"));
                return null;
            }
            if (item.getNumSeleccionadas() > item.getHabitacion().getDisponibles()) {
                String msg = "Habitación de tipo " + item.getHabitacion().getTipo() + " no disponible";
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                return null;
            }
            totalHabitaciones += item.getNumSeleccionadas();
        }

        if (totalHabitaciones == 0) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Debe seleccionar al menos una habitación",
                    "Debe seleccionar al menos una habitación"));
            return null;
        }

        habitacionesBloqueadas = new ArrayList<>();
        try {
            long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
            double total = 0.0;

            for (HabitacionSeleccion item : habitacionesDisponibles) {
                if (item.getNumSeleccionadas() > 0) {
                    boolean bloqueado = gestionReservas.bloquearHabitaciones(
                            hotelSeleccionado.getId(),
                            item.getHabitacion().getTipo(),
                            item.getNumSeleccionadas());
                    if (!bloqueado) {
                        liberarBloqueosActuales();
                        String msg = "Habitación de tipo " + item.getHabitacion().getTipo() + " no disponible";
                        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
                        return null;
                    }
                    habitacionesBloqueadas.add(item);
                    total += item.getNumSeleccionadas() * noches * item.getHabitacion().getPrecioPorNoche();
                }
            }

            this.importeTotal = total;
            this.cliente = new DatosCliente();
            this.pago = new DatosPago();
            return "datosReserva.xhtml";
        } catch (Exception e) {
            liberarBloqueosActuales();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error en acceso a datos", "Error en acceso a datos"));
            return null;
        }
    }

    /**
     * Confirma y almacena la reserva en el sistema.
     */
    public String confirmarReserva() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            reservaConfirmada = gestionReservas.confirmarReserva(cliente, pago);
            if (reservaConfirmada == null) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error en acceso a datos", "Error en acceso a datos"));
                return null;
            }
            habitacionesBloqueadas.clear();
            return "confirmacionReserva.xhtml";
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error en acceso a datos", "Error en acceso a datos"));
            return null;
        }
    }

    /**
     * Libera las habitaciones bloqueadas si el usuario cancela antes de confirmar.
     */
    public String cancelarProcesoReserva() {
        liberarBloqueosActuales();
        return "index.xhtml?faces-redirect=true";
    }

    /**
     * Cierra la confirmacion e invalida la sesion para renovar el EJB Stateful (@Remove en confirmarReserva).
     */
    public String cerrarConfirmacion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    private void liberarBloqueosActuales() {
        if (hotelSeleccionado != null && habitacionesBloqueadas != null) {
            for (HabitacionSeleccion item : habitacionesBloqueadas) {
                try {
                    gestionReservas.liberarHabitaciones(
                            hotelSeleccionado.getId(),
                            item.getHabitacion().getTipo(),
                            item.getNumSeleccionadas());
                } catch (Exception e) {
                    java.util.logging.Logger.getLogger(ReservaBean.class.getName())
                            .log(java.util.logging.Level.WARNING, "Error liberando habitaciones bloqueadas", e);
                }
            }
            habitacionesBloqueadas.clear();
        }
    }

    public TipoTarjeta[] getTiposTarjeta() {
        return TipoTarjeta.values();
    }

    // Getters y setters

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public List<Hotel> getHotelesEncontrados() {
        return hotelesEncontrados;
    }

    public void setHotelesEncontrados(List<Hotel> hotelesEncontrados) {
        this.hotelesEncontrados = hotelesEncontrados;
    }

    public Hotel getHotelSeleccionado() {
        return hotelSeleccionado;
    }

    public void setHotelSeleccionado(Hotel hotelSeleccionado) {
        this.hotelSeleccionado = hotelSeleccionado;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public List<HabitacionSeleccion> getHabitacionesDisponibles() {
        return habitacionesDisponibles;
    }

    public void setHabitacionesDisponibles(List<HabitacionSeleccion> habitacionesDisponibles) {
        this.habitacionesDisponibles = habitacionesDisponibles;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public DatosCliente getCliente() {
        return cliente;
    }

    public void setCliente(DatosCliente cliente) {
        this.cliente = cliente;
    }

    public DatosPago getPago() {
        return pago;
    }

    public void setPago(DatosPago pago) {
        this.pago = pago;
    }

    public Reserva getReservaConfirmada() {
        return reservaConfirmada;
    }

    public void setReservaConfirmada(Reserva reservaConfirmada) {
        this.reservaConfirmada = reservaConfirmada;
    }
}
