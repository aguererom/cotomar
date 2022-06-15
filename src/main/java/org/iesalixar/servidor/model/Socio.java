package org.iesalixar.servidor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="socios")
public class Socio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String numCasa;
	
	@NotNull
	private String nombre;
	
	@NotNull
	private String apellidos;

	@NotNull
	private String importe;

	@NotNull
	private String year;
	
	@Column(nullable=true)
	private String year2;
	
	@Column(nullable=true)
	private String year3;
	
	@Column(nullable=true)
	private String year4;
	
	@Column(nullable=true)
	private String year5;
	
	@NotNull
	@Column(columnDefinition="BOOLEAN")	
	private boolean adeudado;
	
	public Socio() {
		
	}	

	public String getNumCasa() {
		return numCasa;
	}

	public void setNumCasa(String numCasa) {
		this.numCasa = numCasa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}		

	public String getYear2() {
		return year2;
	}
	
	public void setYear2(String year2) {
		this.year2 = year2;
	}
	
	public String getYear3() {
		return year3;
	}

	public void setYear3(String year3) {
		this.year3 = year3;
	}
	
	public String getYear4() {
		return year4;
	}

	public void setYear4(String year4) {
		this.year4 = year4;
	}

	public String getYear5() {
		return year5;
	}
	
	public void setYear5(String year5) {
		this.year5 = year5;
	}
	
	public boolean isAdeudado() {
		return adeudado;
	}

	public void setAdeudado(boolean adeudado) {
		this.adeudado = adeudado;
	}

	@Override
	public String toString() {
		return "Socio '" + numCasa + "', Nombre = " + nombre + ", Apellidos = " + apellidos + ", Importe = " + importe
				+ ", Año = " + year + ", Año = " + year2 + ", Año = " + year3 + ", Año = " + year4 + ", Año = "+ year5 + ", Adeudado = " + adeudado;
	}
	
	

}
