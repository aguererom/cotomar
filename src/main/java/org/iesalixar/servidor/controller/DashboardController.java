package org.iesalixar.servidor.controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.dto.AdminDTO;
import org.iesalixar.servidor.dto.Aforo1DTO;
import org.iesalixar.servidor.dto.SocioDTO;
import org.iesalixar.servidor.model.Socio;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.AforoServiceImpl;
import org.iesalixar.servidor.services.SocioServiceImpl;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DashboardController {
	
	Logger logger = LogManager.getLogger(DashboardController.class);

	@Autowired
	UsuarioServiceImpl usuarioService;
	
	@Autowired
	AforoServiceImpl aforoService;
	
	@Autowired
	SocioServiceImpl socioService;
	
	@Autowired
	HttpSession session;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/admin")
	public String adminLogin(Model model) {

		return "dashboard_login";

	}

	@PostMapping("/admin")
	public RedirectView adminLoginPost(@ModelAttribute AdminDTO admin, Model model) {
		
		Usuario u = usuarioService.getUsuarioByName(admin.getUsername());

		// comprobamos que los campos no son nulos y que coinciden con los valores del
		// usuario de la BBDD. Si posee el rol 'ROLE_ADMIN' y la contraseña introducida
		// coincide con la hasheada, accederá al dashboard
		if (admin.getUsername() != null && u.getUserName().equals(admin.getUsername()) && admin.getPassword() != null
				&& passwordEncoder.matches(admin.getPassword(), u.getPassword())) {

			
			if(u.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
			
				session.setAttribute("nickname", u.getUserName());
				u.setActivo(true);
				usuarioService.actualizarUsuario(u);
				logger.info("session: " + session.getAttribute("nickname"));
				logger.info("Accediendo a Panel de control con las credenciales: " + u.toString());


				return new RedirectView("/admin/index");

				
			}		

		}
		
		logger.info("No se ha podido aceder al Panel de Control con las credenciales: " + u.toString());
		return new RedirectView("/admin");
	}
	/* Hacemos las pruebas con dos navegadores distintos. Puesto que si inicio sesión como "ROLE_ADMIN"
	 * e intento despues iniciar sesión como "ROLE_USER" si recargo la web en el /admin me redirige a
	 * a /index puesto que toma la ultima sesion y toma como usuario el que tiene el rol "ROLE_USER" */
	@GetMapping("/admin/index")
	public String adminZone(Model model) {		
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				model.addAttribute("conectados", usuarioService.getAllConnectedUsers());
				return "dashboard";	
				
			}
			
		} 

		model.addAttribute("conectados", usuarioService.getAllConnectedUsers());
		return "dashboard";		

	}

	@GetMapping("/admin/usuarios")
	public String mostrarUsuarios(Model model) {

		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				model.addAttribute("usuarios", usuarioService.getAllUsers());
				return "usuarios";
				
			}
			
		} 

		model.addAttribute("usuarios", usuarioService.getAllUsers());

		return "usuarios";		
		
	}
	
	@RequestMapping("/admin/usuarios/delete")
	public String eliminarUsuario(@RequestParam(name = "num", required=false) String nombre, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				usuarioService.deleteUsuarioByUserName(nombre);
				logger.info("Usuario eliminado : " + usuarioService.getUsuarioByName(nombre));
				return "redirect:/admin/usuarios";
				
			}
			
		} 
		
		usuarioService.deleteUsuarioByUserName(nombre);
		logger.info("Usuario eliminado : " + usuarioService.getUsuarioByName(nombre));
		return "redirect:/admin/usuarios";
	}

	
	@RequestMapping("/admin/update")
	public String actualizarUsuario(@RequestParam(name = "codigo", required=false) String codigo, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				model.addAttribute("usuario", usuarioService.getUsuarioByName(codigo));
				logger.info("Usuario cargado: " + usuarioService.getUsuarioByName(codigo));
				return "update_usuario";
				
			}
			
		} 
		
		model.addAttribute("usuario", usuarioService.getUsuarioByName(codigo));
		logger.info("Usuario cargado: " + usuarioService.getUsuarioByName(codigo));
		return "update_usuario";
	}

	@PostMapping("/admin/update")
	public String updateUser(@ModelAttribute Usuario user) {
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				user.setPassword(new BCryptPasswordEncoder(15).encode(user.getPassword()));
				
				if (usuarioService.actualizarUsuario(user) == null) {
					
					logger.info("No se ha podido modificar el usuario");
					return "redirect:/admin/index";
				}
				
				logger.info("Usuario modificado correctamente");
				return "redirect:/admin/usuarios";
				
			}
			
		}
		
		logger.info("Usuario modificado correctamente");
		return "redirect:/admin/usuarios";
		
	}
	
	@GetMapping("/admin/socios")
	public String mostrarSocios(Model model) {
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				model.addAttribute("socios", socioService.getAllSocios());

				return "socios";
				
			}
			
		} 

		model.addAttribute("socios", socioService.getAllSocios());

		return "socios";
	}
	
	@GetMapping("/admin/socios/deudas")
	public String mostrarAdeudados(Model model) {
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				model.addAttribute("deudas", socioService.mostrarSociosAdeudados());

				return "deudas";
				
			}
			
		} 

		model.addAttribute("deudas", socioService.mostrarSociosAdeudados());
		return "deudas";
	}
	
	@GetMapping("/admin/socios/create")
	public String createSociosGet(Model model) {
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				SocioDTO socioDTO = new SocioDTO();		
				model.addAttribute("socio", socioDTO);		
				return "create_socio";
				
			}
			
		} 
	
		return "create_socio";
	}
	
	@PostMapping("/admin/socios/create")
	public String createSociosPost(@ModelAttribute SocioDTO socio) {
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				Socio socioBD = new Socio();
				
				socioBD.setNumCasa(socio.getNumCasa());
				socioBD.setNombre(socio.getNombre());
				socioBD.setApellidos(socio.getApellidos());
				socioBD.setYear(socio.getYear());
				socioBD.setYear2(socio.getYear2());
				socioBD.setYear3(socio.getYear3());
				socioBD.setYear4(socio.getYear4());
				socioBD.setYear5(socio.getYear5());
				socioBD.setImporte(socio.getImporte());
				socioBD.setAdeudado(false);
				
				socioBD = socioService.insertSocio(socioBD);
				
				if (socioBD==null) {
					
					return "redirect:/admin/socios/create?error='true'";
					
				}
				
				return "redirect:/admin/socios";
				
			}
			
		} 
		
		return "redirect:/admin/socios";
		
	}
	
	@RequestMapping("/admin/socios/update")
	public String actualizarSocio(@RequestParam(name = "codigo", required=false) String codigo, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				model.addAttribute("socio", socioService.getSocioByNumCasa(codigo));
				return "update_socio";
				
			}
			
		} 
		
		model.addAttribute("socio", socioService.getSocioByNumCasa(codigo));
		return "update_socio";
	}

	@PostMapping("/admin/socios/update")
	public String actualizarSocio(@ModelAttribute Socio socio) {
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				
				if (socioService.actualizarSocio(socio) == null) {
					
					logger.info("No se ha podido modificar el socio");
					return "redirect:/admin/socios";
				}
				
				logger.info("Usuario modificado correctamente");
				return "redirect:/admin/socios";
				
			}
			
		}
		
		logger.info("Usuario modificado correctamente");
		return "redirect:/admin/usuarios";
		
	}
	
	@GetMapping("/admin/aforo")
	public String index(Model model) {
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				model.addAttribute("aforo", aforoService.obtenerAforo());		
				return "aforo";
				
			}
			
		} 
		
		model.addAttribute("aforo", aforoService.obtenerAforo());		
		return "aforo";
		
	}
	
	
	@GetMapping("/admin/aforo/create")
	public String createAforoGet(Model model) {
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				Aforo1DTO aforoDTO = new Aforo1DTO();		
				model.addAttribute("aforo", aforoDTO);		
				return "create_aforo";
				
			}
			
		} 
	
		return "create_aforo";
	}
	
	@PostMapping("/admin/aforo/create")
	public String createAforoPost(@ModelAttribute Aforo1DTO aforo) {
		
		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				if(Integer.parseInt(aforo.getNumPersonas())  >= 0 && Integer.parseInt(aforo.getNumPersonas()) <= 100) {										
					
					if (aforoService.insertAforo(aforo.getNumPersonas()) == null) {
						
						logger.info("No ha sido posible crear el aforo. Debe introducir un número de personas entre 0-100.");
						return "redirect:/admin/aforo/create?error=true";
						
					}
					
				}
				
			}
			
		} 
		
		return "redirect:/admin/aforo";
		
	}
	
	@RequestMapping("/admin/socios/delete")
	public String eliminarSocio(@RequestParam(name = "num", required=false) String numCasa, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String)session.getAttribute("nickname"));
		
		if(u != null) {
			
			if(u.getRole().equals("ROLE_USER") && u.getRole() != null) {
				
				return "/error/403";
				
			} else if(u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {
				
				socioService.deleteSocioByNumCasa(numCasa);
				logger.info("Socio eliminado : " + socioService.getSocioByNumCasa(numCasa));
				return "redirect:/admin/socios";
				
			}
			
		} 
		
		socioService.deleteSocioByNumCasa(numCasa);
		logger.info("Socio eliminado : " + socioService.getSocioByNumCasa(numCasa));
		return "redirect:/admin/socios";
	}
}
