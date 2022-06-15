package org.iesalixar.servidor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.dto.UsuarioDTO;
import org.iesalixar.servidor.dto.UsuarioLoginDTO;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UsuarioServiceImpl usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
		
	@GetMapping("/login")
	public String login(@RequestParam(value = "invalid-session", defaultValue = "false", required = false) boolean invalidSession, @RequestParam(name = "nickname", required=false) String nickname, Model model) {
		
		/*if(invalidSession) {
			
			model.addAttribute("invalidSession", "La sesión ha expirado");
			
		}*/
		
		UsuarioLoginDTO usuarioDTO = new UsuarioLoginDTO();
		model.addAttribute(usuarioDTO);
		return "login";
		
	}
	
	@PostMapping("/login")
	public String userLoginPost(@ModelAttribute UsuarioLoginDTO usuarioDTO, Model model) {

		Usuario u = usuarioService.getUsuarioByName(usuarioDTO.getUsername());
		logger.info("Accediendo a web Cotomar con las credenciales: " + u.toString());

		// comprobamos que los campos no son nulos y que coinciden con los valores del
		// usuario de la BBDD. Si posee el rol 'ROLE_USER' o 'ROLE_ADMIN' y la contraseña introducida
		// coincide con la hasheada, accederá a la web principal
		if (usuarioDTO.getUsername() != null && u.getUserName().equals(usuarioDTO.getUsername()) && usuarioDTO.getPassword() != null
				&& passwordEncoder.matches(usuarioDTO.getPassword(), u.getPassword())) {

			if(u.getRole().equalsIgnoreCase("ROLE_USER")) {
				
				
				session.setAttribute("nickname", u.getUserName());
				logger.info("Creamos atributo de sesion: " + session.getAttribute("username"));
				u.setActivo(true);
				usuarioService.actualizarUsuario(u);
				logger.info("Accediendo a web Cotomar con las credenciales: " + u.toString());
				return "redirect:/index";
				
			}		

		}
		
		logger.info("No se ha podido aceder con las credenciales: " + u.toString());
		return "redirect:/login";
		
	}
	
	@GetMapping("/register")
	public String registerGet(Model model) {
		
		UsuarioDTO userDTO = new UsuarioDTO();		
		model.addAttribute("usuario", userDTO);		
		return "register";
	}
	
	@PostMapping("/register")
	public String registerPost(@ModelAttribute UsuarioDTO usuario) {
		
		Usuario userBD = new Usuario();
		userBD.setActivo(true);
		userBD.setNombre(usuario.getNombre());
		userBD.setApellidos(usuario.getApellidos());
		userBD.setUserName(usuario.getUsuario());
		userBD.setRole("ROLE_USER");
		userBD.setEmail(usuario.getEmail());		
		userBD.setPassword(new BCryptPasswordEncoder(15).encode(usuario.getPassword()));
		
		userBD = usuarioService.insertUsuario(userBD);
		
		if (userBD==null) {
			return "redirect:/register";
		}
		
		return "redirect:/login";
		
	}
	
	@PostMapping("/close")
	public String logout(@RequestParam(name = "nickname", required=false) String nickname, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Usuario u = usuarioService.getUsuarioByName((String)nickname);
		System.out.println((String)nickname);
	    logger.info("Cerramos la sesion del usuario: " + (String)nickname);
	    if (session != null) {	    	
	    	
	    	String rol = u.getRole();
	    	logger.info(u.toString());
	    	u.setActivo(false);
	    	usuarioService.actualizarUsuario(u);
	    	logger.info("Cerramos la sesion del usuario " + u.toString());
	        session.invalidate();
	        
	        if(rol.equals("ROLE_ADMIN")) {
	        	
	        	return "redirect:/admin?logout=true";
	        	
	        }
	    }
	    return "redirect:/login?logout=true";  //Where you go after logout here.
	}
	
}
