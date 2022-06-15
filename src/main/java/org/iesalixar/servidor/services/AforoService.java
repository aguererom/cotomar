package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.Aforo;

public interface AforoService {

	public List<Aforo> obtenerAforo();
	
	public List<Aforo> obtenerAforoDia();

	public Aforo insertAforo(String aforo);

}
