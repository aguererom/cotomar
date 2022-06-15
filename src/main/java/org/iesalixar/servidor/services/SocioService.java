package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.Socio;

public interface SocioService {

	public Socio insertSocio(Socio socio);

	public Socio actualizarSocio(Socio socio);

	public List<Socio> getAllSocios();

	public Socio getSocioByNumCasa(String numCasa);
	
	public void deleteSocioByNumCasa(String numCasa);

}
