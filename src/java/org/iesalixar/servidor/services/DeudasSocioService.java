package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.DeudasSocio;

public interface DeudasSocioService {

	public DeudasSocio insertDeuda(DeudasSocio deuda);

	public DeudasSocio actualizarDeuda(DeudasSocio deuda);

	public List<DeudasSocio> getAllDeudas();

	public List<DeudasSocio> getDeudasByNumCasa(String numCasa);
	
	public void deleteDeudaById(String id);

}
