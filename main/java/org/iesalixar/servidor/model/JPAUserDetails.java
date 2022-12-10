package org.iesalixar.servidor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JPAUserDetails implements UserDetails{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
	private Long id;
	private String apellidos;
	private String email;
	private String role;
	private String password;
	private boolean activo;
	private List<GrantedAuthority> authorities;
	
	public JPAUserDetails(Usuario usuario) {
		
		this.id = usuario.getId();
		this.apellidos = usuario.getApellidos();
		this.email = usuario.getEmail();
		this.role = usuario.getRole();
		this.usuario = usuario.getUserName();
		this.password = usuario.getPassword();
		this.activo = usuario.isActivo();
		this.authorities = new ArrayList<>();				
		this.authorities.add(new SimpleGrantedAuthority(usuario.getRole().toString()));
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.authorities;
	}
	
	

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.usuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.activo;
	}

	@Override
	public String toString() {
		return "JPAUserDetails [usuario=" + usuario + ", id=" + id + ", apellidos=" + apellidos + ", email=" + email
				+ ", role=" + role + ", password=" + password + "]";
	}

	
	
	

	
}
