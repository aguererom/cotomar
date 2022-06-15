package org.iesalixar.servidor.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="aforo")
public class Aforo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String fecha;
	
	@NotNull
	private String hora;
	
	@NotNull
	private String numPersonas;

	public Aforo() {
		super();
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(String numPersonas) {
		this.numPersonas = numPersonas;
	}

	@Override
	public String toString() {
		return "Aforo día: '" + fecha + "', hora: '" + hora + "', Nº Personas: " + numPersonas;
	}		

}
