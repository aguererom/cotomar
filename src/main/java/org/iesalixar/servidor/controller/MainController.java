package org.iesalixar.servidor.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.model.Aforo;
import org.iesalixar.servidor.services.AforoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {	
	
	Logger logger = LogManager.getLogger(MainController.class);
	
	@Autowired
	AforoServiceImpl aforoService;

	@GetMapping("/index")
	public String index(Model model) {
		
		List<Aforo> aforo = aforoService.obtenerAforo();
		model.addAttribute("aforo", aforo.get(0));		
		return "index";
		
	}	

}
