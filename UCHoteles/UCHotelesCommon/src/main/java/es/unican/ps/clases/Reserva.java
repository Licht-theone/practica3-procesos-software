package es.unican.ps.clases;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reserva {
	private long id;
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	private List<ReservaTipoHabitacion> lineasReserva = new ArrayList<ReservaTipoHabitacion>();
	
	public Reserva() {
	}

	public Reserva(long id, LocalDate fechaEntrada, LocalDate fechaSalida, List<ReservaTipoHabitacion> lineasReserva) {
		super();
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.lineasReserva = lineasReserva;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public List<ReservaTipoHabitacion> getLineasReserva() {
		return lineasReserva;
	}

	public void setLineasReserva(List<ReservaTipoHabitacion> lineasReserva) {
		this.lineasReserva = lineasReserva;
	}
	
	public void addLineaReserva(ReservaTipoHabitacion r) {
		if (lineasReserva != null) {
			lineasReserva.add(r);
			r.setReserva(this);
		}
	}

	public double getImporte() {
		return calcularImporte();
	}
	
	private double calcularImporte() {
		double total = 0;
		long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida) - 1;
		for (ReservaTipoHabitacion res: lineasReserva) {
			total += res.getNumHabitaciones() * (noches * res.getHabitacion().getPrecioPorNoche());
		}
		return total;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida
				+ ", lineasReserva=" + lineasReserva + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaEntrada, fechaSalida, Long.valueOf(id), lineasReserva);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(fechaEntrada, other.fechaEntrada) && Objects.equals(fechaSalida, other.fechaSalida)
				&& id == other.id && Objects.equals(lineasReserva, other.lineasReserva);
	}
	
}
