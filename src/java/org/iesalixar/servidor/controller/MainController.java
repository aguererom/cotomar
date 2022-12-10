package org.iesalixar.servidor.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iesalixar.servidor.services.AforoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {	
	
	Logger logger = LogManager.getLogger(MainController.class);
	
	@Autowired
	AforoServiceImpl aforoService;

	@GetMapping("/index")
	public String index(@RequestParam(name = "nickname", required=false) String nickname, Model model) {

		model.addAttribute("aforo1", aforoService.obtenerAforoRecinto("piscina 1").getNumPersonas());
		model.addAttribute("aforo2", aforoService.obtenerAforoRecinto("piscina 2").getNumPersonas());
		model.addAttribute("nickname", nickname);
		return "index";
		
	}	

}
