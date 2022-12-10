package org.iesalixar.servidor.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.model.DeudasSocio;
import org.iesalixar.servidor.repository.DeudasSocioRepository;
import org.iesalixar.servidor.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DeudasSocioServiceImpl implements DeudasSocioService {

	Logger logger = LogManager.getLogger(DeudasSocioServiceImpl.class);

	@Autowired
	SocioRepository socioRepo;

	@Autowired
	DeudasSocioRepository deudaRepo;

	@Override
	public DeudasSocio insertDeuda(DeudasSocio deuda) {
		// comprobamos que el socio no existe antes de crearlo
		if (deuda != null) {

			return deudaRepo.save(deuda);
			
		}

		return null;
	}

	@Override
	public DeudasSocio actualizarDeuda(DeudasSocio deuda) {

		if (deuda == null) {

			logger.info("No ha sido posible actualizar la deuda");
			return null;

		}
		logger.info("Insertamos la deuda al socio: " + deuda.toString());
		return deudaRepo.save(deuda);

	}

	@Override
	public List<DeudasSocio> getAllDeudas() {

		// Obtengo el resultado a través del repositorio
		List<DeudasSocio> deudas = deudaRepo.findAll();

		// Verificando que he obtenido algun registro
		if (deudas != null && deudas.size() > 0) {

			List<DeudasSocio> deudasSocio = new ArrayList<DeudasSocio>();

			for (DeudasSocio s : deudasSocio) {

				if (s.getImporte() != null) {

					deudas.add(s);

				}

			}

			return deudas;
		}
		// No he obtenido nada devuelvo una lista vacía (para no devolver nulo)
		return new ArrayList<DeudasSocio>();

	}

	@Override
	public List<DeudasSocio> getDeudasByNumCasa(String numCasa) {
		
		// Obtengo el resultado a través del repositorio
		List<DeudasSocio> deudas = deudaRepo.findAll();
		
		if (numCasa != null && deudas != null && deudas.size() > 0) {

			for (DeudasSocio s : deudas) {

				// si el socio está adeudado y su número de casa coincide con el pasado por parámetro
				if (s.getImporte() != null && s.getSocio().getNumCasa() == numCasa) {

					deudas.add(s);

				}

			}

			return deudas;

		}

		// No he obtenido nada devuelvo una lista vacía (para no devolver nulo)
		return new ArrayList<DeudasSocio>();
		
	}

	@Override
	public void deleteDeudaById(String id) {
		
		if (id != null) {
			
			logger.info("Eliminamos la deuda " + id);
			deudaRepo.deleteById(Long.parseLong(id));

		}
		logger.info("No ha sido posible eliminar la deuda: " + id);
	}

}
