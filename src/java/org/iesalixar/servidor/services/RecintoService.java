package org.iesalixar.servidor.services;

import java.util.List;
import org.iesalixar.servidor.model.Recinto;

public interface RecintoService {

	public List<Recinto> obtenerRecintos();

	public Recinto insertRecinto(Recinto recinto);
	
	public Recinto getRecintoByNombre(String id);

}
