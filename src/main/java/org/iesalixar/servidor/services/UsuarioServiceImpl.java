package org.iesalixar.servidor.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UsuarioServiceImpl implements UsuarioService {

	Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	UsuarioRepository userRepo;

	@Override
	public List<Usuario> getAllUsers() {

		// Obtengo el resultado a través del repositorio
		List<Usuario> usersBD = userRepo.findAll();

		// Verificando que he obtenido algun registro
		if (usersBD != null && usersBD.size() > 0) {

			return usersBD;
		}

		// No he obtenido nada devuelvo una lista vacía (para no devolver nulo)
		return new ArrayList<Usuario>();

	}
	
	@Override
	public List<Usuario> getAllConnectedUsers() {

		// Obtengo el resultado a través del repositorio
		List<Usuario> usersBD = userRepo.findAll();
		List<Usuario> connectedUsers = new ArrayList<Usuario>();
		
		// Verificando que he obtenido algun registro
		if (usersBD != null && usersBD.size() > 0) {
			
			for(Usuario u : usersBD) {
				
				if(u.isActivo()) {
				
					connectedUsers.add(u);
					
				}			
				
			}
			
			return connectedUsers;
		}

		// No he obtenido nada devuelvo una lista vacía (para no devolver nulo)
		return new ArrayList<Usuario>();

	}

	@Override
	public Usuario insertUsuario(Usuario usuario) {

		if (usuario != null) {

			return userRepo.save(usuario);
		}

		return null;

	}

	@Override
	public Usuario actualizarUsuario(Usuario u) {

		if (u == null || u.getId() == null || u.getUserName() == null) {
			return null;
		}

		return userRepo.save(u);

	}

	@Override
	public Usuario getUsuarioByName(String nombre) {

		if (nombre != null) {

			Usuario user = userRepo.findByUserName(nombre);

			return user;

		}

		return null;
	}

	@Override
	public void deleteUsuarioByUserName(String userName) {
		
		if (userName != null) {
			
			logger.info("Eliminamos el usuario " + userName);
			userRepo.deleteByUserName(userName);

		}
		
	}

}
