package es.unican.ps.clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.unican.ps.enums.Categoria;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Clase hotel de la aplicacion de gestion hoteles
 * @author aaron
 */
@Entity
public class Hotel implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private String localidad;
	private String direccion;
	private Categoria categoria;
	
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Habitacion> habitaciones = new ArrayList<Habitacion>();
	
	
	
	public Hotel() {
	}
	
	/**
	 * Constructor de la clase
	 * @param id id del hotel
	 * @param nombre nombre del hotel
	 * @param localidad localidad del hotel
	 * @param direccion direccion del hotel
	 * @param categoria categoria del hotel
	 */
	public Hotel(long id, String nombre, String localidad, String direccion, Categoria categoria,
			List<Habitacion> habitaciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.localidad = localidad;
		this.direccion = direccion;
		this.categoria = categoria;
		this.habitaciones = habitaciones;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}
	
	public void addHabitacion(Habitacion h) {
		if (habitaciones != null) {
			habitaciones.add(h);
			h.setHotel(this);
		}
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", nombre=" + nombre + ", localidad=" + localidad + ", direccion=" + direccion
				+ ", categoria=" + categoria + ", habitaciones=" + habitaciones + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, direccion, habitaciones, Long.valueOf(id), localidad,
				nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		return categoria == other.categoria && Objects.equals(direccion, other.direccion)
				&& Objects.equals(habitaciones, other.habitaciones) && id == other.id
				&& Objects.equals(localidad, other.localidad) && Objects.equals(nombre, other.nombre);
	}
	
	
}
