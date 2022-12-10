package org.iesalixar.servidor.services;

import java.util.List;

import org.iesalixar.servidor.model.Usuario;

public interface UsuarioService {

	public Usuario insertUsuario(Usuario usuario);

	public Usuario actualizarUsuario(Usuario usuario);

	public List<Usuario> getAllUsers();

	public Usuario getUsuarioByName(String nombre);

	public List<Usuario> getAllConnectedUsers();
	
	public void deleteUsuarioByUserName(String userName);


}
