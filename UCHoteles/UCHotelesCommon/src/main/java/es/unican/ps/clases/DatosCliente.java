package es.unican.ps.clases;

import java.util.Objects;

public class DatosCliente {
	private String dni;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String email;
	
	public DatosCliente(String dni, String nombre, String primerApellido, String segundoApellido, String email) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "DatosCliente [dni=" + dni + ", nombre=" + nombre + ", primerApellido=" + primerApellido
				+ ", segundoApellido=" + segundoApellido + ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni, email, nombre, primerApellido, segundoApellido);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosCliente other = (DatosCliente) obj;
		return Objects.equals(dni, other.dni) && Objects.equals(email, other.email)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(primerApellido, other.primerApellido)
				&& Objects.equals(segundoApellido, other.segundoApellido);
	}
	
}
