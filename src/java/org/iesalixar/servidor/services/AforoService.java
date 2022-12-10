package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.Aforo;
import org.iesalixar.servidor.model.Recinto;

public interface AforoService {

	public List<Aforo> obtenerAforo();
	
	public List<Aforo> obtenerAforoDia();

	public Aforo insertAforo(String numPersonas, Recinto recinto);
	
	public Aforo obtenerAforoRecinto(String nombre);
	
}
