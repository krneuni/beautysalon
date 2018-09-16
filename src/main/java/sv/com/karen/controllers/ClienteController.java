package sv.com.karen.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sv.com.karen.models.entities.Cliente;
import sv.com.karen.models.services.IClienteService; 

@Controller
@SessionAttributes("cliente")
@RequestMapping(value="/cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@RequestMapping(value="/cliente", method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo","listado de clientes");
		model.addAttribute("clientes", clienteService.findAll());
		return "cliente";
	}
	
	@RequestMapping(value="/agregar", method=RequestMethod.GET)
	public String form(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		return "agregar";
	}
	
	@RequestMapping(value="/agregar", method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult bindingResult, RedirectAttributes flash, SessionStatus sessionStatus ) {
		if(bindingResult.hasErrors()) {
			return "agregar";
		}
		clienteService.save(cliente);
		sessionStatus.setComplete();
		
		flash.addFlashAttribute("success","Cliente creado con exito");
		return "redirect:/cliente/cliente";
	}
	
	@RequestMapping(value="/agregar/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
		}else {
			flash.addFlashAttribute("error","El Id del cliente no puede ser cero");
			return "redirect:/cliente/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		
		return "agregar";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if (id > 0 ) {
			clienteService.delete(id);
		}
		flash.addFlashAttribute("success","Cliente eliminado con exito");
		return "redirect:/cliente/cliente";
	}
	
}
