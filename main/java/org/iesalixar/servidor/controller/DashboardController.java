package org.iesalixar.servidor.controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.dto.AdminDTO;
import org.iesalixar.servidor.dto.AforoDTO;
import org.iesalixar.servidor.dto.DeudasSocioDTO;
import org.iesalixar.servidor.dto.RecintoDTO;
import org.iesalixar.servidor.dto.SocioDTO;
import org.iesalixar.servidor.model.DeudasSocio;
import org.iesalixar.servidor.model.Recinto;
import org.iesalixar.servidor.model.Socio;
import org.iesalixar.servidor.model.Usuario;
import org.iesalixar.servidor.services.AforoServiceImpl;
import org.iesalixar.servidor.services.DeudasSocioServiceImpl;
import org.iesalixar.servidor.services.RecintoServiceImpl;
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
	RecintoServiceImpl recintoService;

	@Autowired
	SocioServiceImpl socioService;

	@Autowired
	DeudasSocioServiceImpl deudaService;

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

			if (u.getRole().equalsIgnoreCase("ROLE_ADMIN")) {

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

	/*
	 * Hacemos las pruebas con dos navegadores distintos. Puesto que si inicio
	 * sesión como "ROLE_ADMIN" e intento despues iniciar sesión como "ROLE_USER" si
	 * recargo la web en el /admin me redirige a a /index puesto que toma la ultima
	 * sesion y toma como usuario el que tiene el rol "ROLE_USER"
	 */
	@GetMapping("/admin/index")
	public String adminZone(Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				model.addAttribute("conectados", usuarioService.getAllConnectedUsers());
				return "dashboard";

			}

		}

		model.addAttribute("conectados", usuarioService.getAllConnectedUsers());
		return "dashboard";

	}

	@GetMapping("/admin/usuarios")
	public String mostrarUsuarios(Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				model.addAttribute("usuarios", usuarioService.getAllUsers());
				return "usuarios";

			}

		}

		model.addAttribute("usuarios", usuarioService.getAllUsers());

		return "usuarios";

	}

	@RequestMapping("/admin/usuarios/delete")
	public String eliminarUsuario(@RequestParam(name = "num", required = false) String nombre, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

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
	public String actualizarUsuario(@RequestParam(name = "codigo", required = false) String codigo, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

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

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

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

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				model.addAttribute("socios", socioService.getAllSocios());

				return "socios";

			}

		}

		model.addAttribute("socios", socioService.getAllSocios());

		return "socios";
	}

	@GetMapping("/admin/socios/deuda")
	public String mostrarAdeudados(Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				model.addAttribute("deudas", deudaService.getAllDeudas());

				return "deuda";

			}

		}

		model.addAttribute("deudas", deudaService.getAllDeudas());
		return "deuda";
	}

	@GetMapping("/admin/socios/create")
	public String createSociosGet(Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				SocioDTO socioDTO = new SocioDTO();
				model.addAttribute("socio", socioDTO);
				return "create_socio";

			}

		}

		return "create_socio";
	}

	@PostMapping("/admin/socios/create")
	public String createSociosPost(@ModelAttribute SocioDTO socio) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				Socio socioBD = new Socio();

				socioBD.setDni(socio.getDni());
				socioBD.setNumCasa(socio.getNumCasa());
				socioBD.setNombre(socio.getNombre());
				socioBD.setApellidos(socio.getApellidos());
				socioBD.setDireccion(socio.getDireccion());
				socioBD.setCp(socio.getCp());

				socioBD = socioService.insertSocio(socioBD);

				if (socioBD == null) {

					return "redirect:/admin/socios/create?error='true'";

				}

				return "redirect:/admin/socios";

			}

		}

		return "redirect:/admin/socios";

	}

	@GetMapping("/admin/socios/update")
	public String actualizarSocio(@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "error", required = false, defaultValue = "") String error,
			@RequestParam(name = "completado", required = false, defaultValue = "") String completado, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				model.addAttribute("socio", socioService.getSocioByNumCasa(codigo));
				model.addAttribute("error", error);
				model.addAttribute("completado", completado);
				return "update_socio";

			}

		}

		model.addAttribute("socio", socioService.getSocioByNumCasa(codigo));
		return "update_socio";
	}

	@PostMapping("/admin/socios/update")
	public String actualizarSocio(@ModelAttribute Socio socio) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				if (socioService.actualizarSocio(socio) == null) {

					logger.info("No se ha podido modificar el socio");
					return "redirect:/admin/socios?error=" + "No se ha podido modificar el socio.";

				}

			}

		}

		logger.info("Socio modificado correctamente");
		return "redirect:/admin/socios?completado=" + "Socio modificado correctamente.";

	}

	// DEUDA

	@RequestMapping("/admin/socios/deuda/create")
	public String createDeudaSocio(@RequestParam(name = "error", required = false, defaultValue = "") String error,
			@RequestParam(name = "codigo", required = false, defaultValue = "") String codigo,
			@RequestParam(name = "numCasa", required = false) String numCasa, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				DeudasSocioDTO deudaDTO = new DeudasSocioDTO();

				model.addAttribute("deuda", deudaDTO);
				model.addAttribute("error", error);
				model.addAttribute("codigo", codigo);
				return "create_deuda";

			}

		}

		return "create_deuda";
	}

	@PostMapping("/admin/socios/deuda/create")
	public String createDeudaSocio(@ModelAttribute DeudasSocioDTO deuda) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				if (socioService.getSocioByNumCasa(deuda.getSocio()) == null) {

					logger.info("No ha sido posible crear la deuda. El socio no existe.");
					return "redirect:/admin/socios/deuda/create?error="
							+ "No ha sido posible crear la deuda. El socio no existe.";

				} else {

					DeudasSocio deudaBD = new DeudasSocio();

					deudaBD.setSocio(socioService.getSocioByNumCasa(deuda.getSocio()));
					deudaBD.setYear(deuda.getYear());
					deudaBD.setImporte(deuda.getImporte());

					deudaBD = deudaService.insertDeuda(deudaBD);

					return "redirect:/admin/socios/deuda/create?codigo=" + "Deuda creada correctamente.";

				}

			}

		}

		return "redirect:/admin/socios/deuda";

	}

	// ELIMINAR SOCIO

	@RequestMapping("/admin/socios/delete")
	public String eliminarSocio(@RequestParam(name = "num", required = false) String numCasa, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				socioService.deleteSocioByNumCasa(numCasa);
				logger.info("Socio eliminado : " + socioService.getSocioByNumCasa(numCasa));
				return "redirect:/admin/socios";

			}

		}

		socioService.deleteSocioByNumCasa(numCasa);
		logger.info("Socio eliminado : " + socioService.getSocioByNumCasa(numCasa));
		return "redirect:/admin/socios";
	}

	// AFORO

	@GetMapping("/admin/aforo")
	public String index(Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				model.addAttribute("aforo", aforoService.obtenerAforo());
				return "aforo";

			}

		}

		model.addAttribute("aforo", aforoService.obtenerAforo());
		return "aforo";

	}

	@GetMapping("/admin/aforo/create")
	public String createAforoGet(@RequestParam(name = "error", required = false, defaultValue = "") String error,
			@RequestParam(name = "max_aforo", required = false, defaultValue = "100") String max_aforo, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				AforoDTO aforoDTO = new AforoDTO();
				model.addAttribute("aforo", aforoDTO);
				model.addAttribute("recintos", recintoService.obtenerRecintos());
				model.addAttribute("error", error);
				model.addAttribute("max_aforo", max_aforo);
				return "create_aforo";

			}

		}

		return "create_aforo";
	}

	@PostMapping("/admin/aforo/create")
	public String createAforoPost(@ModelAttribute AforoDTO aforo) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				if (Integer.parseInt(aforo.getNumPersonas()) >= 0
						&& Integer.parseInt(aforo.getNumPersonas()) <= recintoService
								.getRecintoByNombre(aforo.getNombre_recinto()).getAforoMax()) {
					

					if (aforoService.insertAforo(aforo.getNumPersonas(),
							recintoService.getRecintoByNombre(aforo.getNombre_recinto())) == null) {

						logger.info(
								"No ha sido posible crear el aforo. Debe introducir un número de personas entre 0-100.");
						return "redirect:/admin/aforo/create?error="
								+ "No ha sido posible crear el aforo. Debe introducir un número de personas entre 0-100.";

					}
					// si el número de personas excede al del aforo máximo del recinto no se puede
					// crear el aforo
				} else {

					logger.info("No ha sido posible crear el aforo. El aforo máximo permitido en este recinto es de "
							+ recintoService.getRecintoByNombre(aforo.getNombre_recinto()).getAforoMax() + " personas");
					// enviamos el mensaje de error en caso de no ser posible crear el aforo al ser
					// excedido (ej. aforo max 90 y usuario teclea 91)
					return "redirect:/admin/aforo/create?error="
							+ "No ha sido posible crear el aforo. El aforo mÃ¡ximo permitido en este recinto es de "
							+ recintoService.getRecintoByNombre(aforo.getNombre_recinto()).getAforoMax() + " personas.";

				}

			}

		}

		return "redirect:/admin/aforo";

	}

	// RECINTO

	@GetMapping("/admin/recinto")
	public String indexRecinto(Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				model.addAttribute("recintos", recintoService.obtenerRecintos());
				return "recinto";

			}

		}

		model.addAttribute("aforo", aforoService.obtenerAforo());
		return "recinto";

	}

	@GetMapping("/admin/recinto/create")
	public String createRecintoGet(@RequestParam(name = "error", required = false, defaultValue = "") String error, @RequestParam(name = "codigo", required = false, defaultValue = "") String codigo, Model model) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				RecintoDTO recintoDTO = new RecintoDTO();
				model.addAttribute("recinto", recintoDTO);
				model.addAttribute("recintos", recintoService.obtenerRecintos());
				model.addAttribute("error", error);
				model.addAttribute("codigo", codigo);
				return "create_recinto";

			}

		}

		return "create_aforo";
	}

	@PostMapping("/admin/recinto/create")
	public String createRecintoPost(@ModelAttribute RecintoDTO recinto) {

		Usuario u = usuarioService.getUsuarioByName((String) session.getAttribute("nickname"));

		if (u != null) {

			if (u.getRole().equals("ROLE_USER") && u.getRole() != null) {

				return "/error/403";

			} else if (u.getRole().equals("ROLE_ADMIN") && u.getRole() != null) {

				if (Integer.parseInt(recinto.getAforoMax()) >= 0 && recinto.getNombre() != null) {

					if (recintoService.getRecintoByNombre(recinto.getNombre()) == null) {

						
						Recinto recintoBD = new Recinto();

						recintoBD.setNombre(recinto.getNombre());
						recintoBD.setDireccion(recinto.getDireccion());
						recintoBD.setAforoMax(Integer.parseInt(recinto.getAforoMax()));

						recintoBD = recintoService.insertRecinto(recintoBD);
						
						
						logger.info("Recinto creado correctamente.");
						return "redirect:/admin/recinto/create?codigo=" + "Recinto creado correctamente.";


					} 
					logger.info("No ha sido posible crear el recinto.");
					return "redirect:/admin/recinto/create?error=" + "No ha sido posible crear el recinto.";
					
				}

			}

		}

		return "redirect:/admin/recinto";

	}
}
