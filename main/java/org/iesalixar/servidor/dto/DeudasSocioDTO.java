package org.iesalixar.servidor.dto;

public class DeudasSocioDTO {
	
	private String socio;
	
	private String year;
	
	private String importe;
	
	public DeudasSocioDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getSocio() {
		return socio;
	}


	public void setSocio(String socio) {
		this.socio = socio;
	}


	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}		

}
