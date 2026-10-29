package es.unican.ps.clases;

import java.util.Objects;

import es.unican.ps.enums.TipoHabitacion;

public class Habitacion {
	private long id;
	private Hotel hotel;
	private int disponibles;
	private TipoHabitacion tipo;
	private double precioPorNoche;
	
	public Habitacion() {
	}
	
	public Habitacion(long id, Hotel hotel, int disponibles, TipoHabitacion tipo, double precioPorNoche) {
		super();
		this.id = id;
		this.hotel = hotel;
		this.disponibles = disponibles;
		this.tipo = tipo;
		this.precioPorNoche = precioPorNoche;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public int getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}

	public TipoHabitacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoHabitacion tipo) {
		this.tipo = tipo;
	}

	public double getPrecioPorNoche() {
		return precioPorNoche;
	}

	public void setPrecioPorNoche(double precioPorNoche) {
		this.precioPorNoche = precioPorNoche;
	}

	@Override
	public String toString() {
		return "Habitacion [id=" + id + ", hotel=" + hotel + ", disponibles=" + disponibles + ", tipo=" + tipo
				+ ", precioPorNoche=" + precioPorNoche + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(disponibles), hotel, Long.valueOf(id), Double.valueOf(precioPorNoche),
				tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habitacion other = (Habitacion) obj;
		return disponibles == other.disponibles && Objects.equals(hotel, other.hotel) && id == other.id
				&& Double.doubleToLongBits(precioPorNoche) == Double.doubleToLongBits(other.precioPorNoche)
				&& tipo == other.tipo;
	}

	
	
}
