package org.iesalixar.servidor.services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.model.Aforo;
import org.iesalixar.servidor.model.Recinto;
import org.iesalixar.servidor.repository.AforoRepository;
import org.iesalixar.servidor.repository.RecintoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AforoServiceImpl implements AforoService {

	Logger logger = LogManager.getLogger(AforoServiceImpl.class);

	@Autowired
	AforoRepository aforoRepo;

	@Autowired
	RecintoRepository recintoRepo;

	@Override
	public Aforo insertAforo(String numPersonas, Recinto recinto) {

		if (numPersonas != null) {
			Aforo aforo = new Aforo();
			aforo.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())));
			aforo.setHora(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
			aforo.setNumPersonas(numPersonas);
			aforo.setRecinto(recinto);
			return aforoRepo.save(aforo);
		}

		return null;

	}

	public Aforo crearAforoApertura() {

		Aforo aforo = new Aforo();
		aforo.setFecha(new SimpleDateFormat().format(new Date(System.currentTimeMillis())));
		aforo.setHora(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
		aforo.setNumPersonas("0");
		return aforoRepo.save(aforo);

	}

	@Override
	public List<Aforo> obtenerAforo() {
		// Obtengo el resultado a través del repositorio
		List<Aforo> aforo = aforoRepo.findByOrderByIdDesc();

		// Verificando que he obtenido algun registro
		if (aforo != null && aforo.size() > 0) {

			return aforo;
		}

		// No he obtenido nada devuelvo una lista vacía (para no devolver nulo)
		return new ArrayList<Aforo>();
	}

	@Override
	public List<Aforo> obtenerAforoDia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Aforo obtenerAforoRecinto(String nombre) {

		// Obtengo el resultado a través del repositorio
		List<Aforo> aforo = aforoRepo.findByOrderByIdDesc();

		// Verificando que he obtenido algun registro
		if (aforo != null && aforo.size() > 0) {

			for(Aforo a : aforo) {
				
				if(a.getRecinto().getNombre().equalsIgnoreCase(nombre)) {
					
					return a;
					
				} 
				
			}		
			
		}
		
		return null;
		
	}

}
