package es.unican.ps.clases;

import java.util.Objects;

public class ReservaTipoHabitacion {
	private long id;
	private Reserva reserva;
	private int numHabitaciones;
	private Habitacion habitacion;
	public ReservaTipoHabitacion(long id, Reserva reserva, int numHabitaciones, Habitacion habitacion) {
		super();
		this.id = id;
		this.reserva = reserva;
		this.numHabitaciones = numHabitaciones;
		this.habitacion = habitacion;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Reserva getReserva() {
		return reserva;
	}
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	public int getNumHabitaciones() {
		return numHabitaciones;
	}
	public void setNumHabitaciones(int numHabitaciones) {
		this.numHabitaciones = numHabitaciones;
	}
	public Habitacion getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	@Override
	public String toString() {
		return "ReservaTipoHabitacion [id=" + id + ", reserva=" + reserva + ", numHabitaciones=" + numHabitaciones
				+ ", habitacion=" + habitacion + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(habitacion, Long.valueOf(id), Integer.valueOf(numHabitaciones), reserva);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReservaTipoHabitacion other = (ReservaTipoHabitacion) obj;
		return Objects.equals(habitacion, other.habitacion) && id == other.id
				&& numHabitaciones == other.numHabitaciones && Objects.equals(reserva, other.reserva);
	}
	
	
}
