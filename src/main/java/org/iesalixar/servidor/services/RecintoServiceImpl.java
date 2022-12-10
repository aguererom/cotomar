package org.iesalixar.servidor.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.model.Recinto;
import org.iesalixar.servidor.repository.RecintoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecintoServiceImpl implements RecintoService {

	Logger logger = LogManager.getLogger(RecintoServiceImpl.class);

	@Autowired
	RecintoRepository recintoRepo;

	@Override
	public List<Recinto> obtenerRecintos() {
		// Obtengo el resultado a través del repositorio
		List<Recinto> recintoBD = recintoRepo.findAll();

		// Verificando que he obtenido algun registro
		if (recintoBD != null && recintoBD.size() > 0) {

			return recintoBD;
		}

		// No he obtenido nada devuelvo una lista vacía (para no devolver nulo)
		return new ArrayList<Recinto>();
	}

	@Override
	public Recinto insertRecinto(Recinto recinto) {
		
		if (recinto != null) {

			return recintoRepo.save(recinto);
		}

		return null;
	}

	@Override
	public Recinto getRecintoByNombre(String id) {
		
		if (id != null) {

			Recinto recinto = recintoRepo.findByNombre(id);

			return recinto;

		}

		return null;
	
	}
	
}
