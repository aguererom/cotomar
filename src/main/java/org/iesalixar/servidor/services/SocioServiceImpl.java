package org.iesalixar.servidor.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.model.Socio;
import org.iesalixar.servidor.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SocioServiceImpl implements SocioService {

	Logger logger = LogManager.getLogger(SocioServiceImpl.class);
	
	@Autowired
	SocioRepository socioRepo;

	@Override
	public List<Socio> getAllSocios() {

		// Obtengo el resultado a través del repositorio
		List<Socio> socios = socioRepo.findByOrderByNumCasaAsc();

		// Verificando que he obtenido algun registro
		if (socios != null && socios.size() > 0) {

			return socios;
		}

		// No he obtenido nada devuelvo una lista vacía (para no devolver nulo)
		return new ArrayList<Socio>();

	}	
	
	public List<Socio> mostrarSociosAdeudados(){
		
		// Obtengo el resultado a través del repositorio
		List<Socio> socios = socioRepo.findAll();
		
		// Verificando que he obtenido algun registro
		if (socios != null && socios.size() > 0) {
			
			List<Socio> deudas = new ArrayList<Socio>();
			
			for(Socio s : socios) {
				
				if(s.isAdeudado()) {
					
					deudas.add(s);
					
				}
				
			}
			
			return deudas;
		}
		// No he obtenido nada devuelvo una lista vacía (para no devolver nulo)
		return new ArrayList<Socio>();
	}

	@Override
	public Socio insertSocio(Socio socio) {
		
		// comprobamos que el socio no existe antes de crearlo
		if (socio != null && socioRepo.findByNumCasa(socio.getNumCasa()) == null) {

			return socioRepo.save(socio);
		}

		return null;

	}

	@Override
	public Socio actualizarSocio(Socio socio) {

		if (socio == null || socio.getNumCasa() == null) {
			
			return null;
			
		}

		return socioRepo.save(socio);

	}

	@Override
	public Socio getSocioByNumCasa(String numCasa) {

		if (numCasa != null) {

			Socio socio = socioRepo.findByNumCasa(numCasa);

			return socio;

		}

		return null;
	}

	@Override
	public void deleteSocioByNumCasa(String numCasa) {
		
		if (numCasa != null) {
			
			logger.info("Eliminamos el socio " + numCasa);
			socioRepo.deleteByNumCasa(numCasa);

		}
		
	}
	
	

}
