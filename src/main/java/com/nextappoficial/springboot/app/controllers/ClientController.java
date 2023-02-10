package com.nextappoficial.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.nextappoficial.springboot.app.models.dao.IClientDao;
import com.nextappoficial.springboot.app.models.entity.Client;
import com.nextappoficial.springboot.app.models.service.IClientService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("client")
public class ClientController {
	
	/* Inyectamos El Tipo Mas Generico Para Las Consultas, Son Generalmente Las Interfaces */
	/* Al Inyectar Se Busca Un Componente (Bean) Registrado Que Implemente Dicha Interface, El ServiceImplement*/
	/* Utilizamos El Qualifier, Cuando Más De Un Componente (Bean), Implementa La Misma Interface */
	//@Autowired
	//@Qualifier("ClientDaoJPA")
	//private IClientDao clientDao;
	
	/* Mejora: Ahora, No Inyectamos Los Daos Directamente, Sino, Que Inyectamos La Clase Service */
	/* Esta Clase Implementa La Iterface, Que Tiene Todos Los Métodos Dao */
	@Autowired
	private IClientService clientService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("title", "Listado De Clientes");
		//model.addAttribute("clients", clientDao.findAll());
		model.addAttribute("clients", clientService.findAll());
		
		return "list";
	}
	
	@RequestMapping(value = "/form")
	public String create(Map<String, Object> model) {
		Client client = new Client();
		
		model.put("title", "Formulario Para Crear Cliente");
		model.put("client", client);
		
		return "form";
	}
	 
	@RequestMapping(value = "/form/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client = null;
		
		if (id > 0) {
			//client = clientDao.findOne(id);
			client = clientService.findOne(id);
			flash.addFlashAttribute("error", "El ID Del Cliente No Existe En El Sistema");
		} else {
			flash.addFlashAttribute("error", "El ID Del Cliente No Puede Ser Cero");
			return "redirect:/list";
		}
		
		model.put("title", "Formulario Para Editar Cliente");
		model.put("client", client);
		
		return "form";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String saveClient(@Valid Client client, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Formulario Para Crear Cliente");
			return "form";
		}
		
		//clientDao.save(client);
		clientService.save(client);
		status.isComplete();
		
		String messageFlash = (client.getId() != null) ? "Cliente Actualizado Exitosamente" : "Cliente Creado Exitosamente";
		flash.addFlashAttribute("success", messageFlash);
		
		return "redirect:list";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			//clientDao.delete(id);
			clientService.delete(id);
			flash.addFlashAttribute("success", "Cliente Eliminado Exitosamente");
		}
		
		return "redirect:/list";
	}
}