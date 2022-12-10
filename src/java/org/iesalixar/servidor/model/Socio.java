package org.iesalixar.servidor.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	private String dni;
	
	@Column(unique = true)
	@NotNull
	private String numCasa;
	
	@NotNull
	private String nombre;
	
	@NotNull
	private String apellidos;

	@NotNull
	private String direccion;
	
	@NotNull
	private String cp;

	@OneToMany(mappedBy="socio",cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<DeudasSocio> socio = new HashSet<>();
	
	public Socio() {
		
	}	

	public String getDni() {
		return dni;
	}



	public void setDni(String dni) {
		this.dni = dni;
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



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public String getCp() {
		return cp;
	}
	

	public void setCp(String cp) {
		this.cp = cp;
	}

	public Set<DeudasSocio> getSocio() {
		return socio;
	}

	public void setSocio(Set<DeudasSocio> socio) {
		this.socio = socio;
	}

	@Override
	public String toString() {
		return "DNI = " + dni + ", Nº Casa =" + numCasa + ", Nombre = " + nombre + ", Apellidos = "
				+ apellidos + ", Direccion = " + direccion + ", CP = " + cp + ", Deuda = " + socio;
	}	

}
