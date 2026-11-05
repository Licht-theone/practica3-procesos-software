package es.unican.ps.clases;

import java.io.Serializable;
import java.util.Objects;

import es.unican.ps.enums.TipoTarjeta;
import jakarta.persistence.Embeddable;

/**
 * Clase que almacena los datos de pago de un cliente para una reserva
 * @author aaron
 */
@Embeddable
public class DatosPago implements Serializable {
	private String numTarjeta;
	private String cvc;
	private int mesCaducidad;
	private int anhoCaducidad;
	private TipoTarjeta tipo;
	
	/**
	 * Constructor de la clase
	 * @param numTarjeta numero de la tarjeta en formato texto
	 * @param cvc cvc en formato texto
	 * @param mesCaducidad mes como numero
	 * @param anhoCaducidad año como numero
	 * @param tipo tipo de tarjeta como en el Enum
	 */
	public DatosPago(String numTarjeta, String cvc, int mesCaducidad, int anhoCaducidad, TipoTarjeta tipo) {
		super();
		this.numTarjeta = numTarjeta;
		this.cvc = cvc;
		this.mesCaducidad = mesCaducidad;
		this.anhoCaducidad = anhoCaducidad;
		this.tipo = tipo;
	}

	public String getNumTarjeta() {
		return numTarjeta;
	}

	public void setNumTarjeta(String numTarjeta) {
		this.numTarjeta = numTarjeta;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public int getMesCaducidad() {
		return mesCaducidad;
	}

	public void setMesCaducidad(int mesCaducidad) {
		this.mesCaducidad = mesCaducidad;
	}

	public int getAnhoCaducidad() {
		return anhoCaducidad;
	}

	public void setAnhoCaducidad(int anhoCaducidad) {
		this.anhoCaducidad = anhoCaducidad;
	}

	public TipoTarjeta getTipo() {
		return tipo;
	}

	public void setTipo(TipoTarjeta tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "DatosPago [numTarjeta=" + numTarjeta + ", cvc=" + cvc + ", mesCaducidad=" + mesCaducidad
				+ ", anhoCaducidad=" + anhoCaducidad + ", tipo=" + tipo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(anhoCaducidad), cvc, Integer.valueOf(mesCaducidad), numTarjeta, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosPago other = (DatosPago) obj;
		return anhoCaducidad == other.anhoCaducidad && Objects.equals(cvc, other.cvc)
				&& mesCaducidad == other.mesCaducidad && Objects.equals(numTarjeta, other.numTarjeta)
				&& tipo == other.tipo;
	}
	
}
