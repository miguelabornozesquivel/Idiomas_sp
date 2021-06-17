package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.spring.model.Profesor;
import pe.edu.upc.spring.service.IProfesorService;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {

	@Autowired
	private IProfesorService srvProfesor;
	
	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		model.put("listProfesor", srvProfesor.listar());
		return "listProfesor";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("profesor", new Profesor());
		return "profesor";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute Profesor objProfesor, BindingResult binRes, Model model) throws ParseException {
		if (binRes.hasErrors())
			return "profesor";
		else {
			boolean flag = srvProfesor.insertar(objProfesor);
			if (flag)
				return "redirect:/profesor/listar";
			else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/profesor/irRegistrar";
			}
		}
		
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Profesor> objProfesor = srvProfesor.listarPorId(id);
		if (objProfesor == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/profesor/listar";
		}
		else {
			model.addAttribute("profesor", objProfesor);
			return "profesor";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if (id != null && id > 0) {
				srvProfesor.eliminar(id);
				model.put("listProfesor", srvProfesor.listar());
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "Ocurrió un error");
			model.put("listProfesor", srvProfesor.listar());
		}
		return "listProfesor";
	}
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listProfesor", srvProfesor.listar());
		return "listProfesor";
	}
	
	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {
		model.addAttribute("profesor", new Profesor());
		return "searchProfesor";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Profesor profesor) throws ParseException {
		List<Profesor> listProfesor;
		listProfesor = srvProfesor.buscarPorFiltro(profesor.getNombre());
		if (listProfesor.isEmpty()) {
			model.put("mensaje", "No se encontraron coincidencias.");
		}
		model.put("listProfesor", listProfesor);
		return "searchProfesor";
	}
}
