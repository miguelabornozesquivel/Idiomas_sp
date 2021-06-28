package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.spring.model.Profesor;
import pe.edu.upc.spring.service.IProfesorService;

@Controller
@RequestMapping("/profesores")
public class ProfesorController {

	static final String MSG_SCS_CRT = "El profesor ha sido guardado de manera exitosa.";
	static final String MSG_SCS_EDT = "El profesor ha sido recuperado de manera exitosa.";
	static final String MSG_SCS_DLT = "El profesor ha sido eliminado de manera exitosa.";
	static final String MSG_ERR_CRT = "Lo sentimos, ocurrió un error mientras se intentaba guardar el profesor.";
	static final String MSG_ERR_EDT = "Lo sentimos, ocurrió un error mientras se intentaba recuperar el profesor.";
	static final String MSG_ERR_DLT = "Lo sentimos, ocurrió un error mientras se intentaba eliminar el profesor.";
	static final String MSG_EXS_NMB = "El correo electrónico ingresado ya existe en el sistema.";
	static final String MSG_NOT_FND = "No se encontraron coincidencias.";
	
	@Autowired
	private IProfesorService srvProfesor;
	
	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		model.put("profesor", new Profesor());
		model.put("listProfesor", srvProfesor.listar());
		return "listProfesor";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("profesor", new Profesor());
		return "profesor";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@Validated({Profesor.StepOneVal.class}) @ModelAttribute Profesor objProfesor, BindingResult binRes, Model model, RedirectAttributes objRedi) throws ParseException {
		if (binRes.hasErrors())
			return "profesor";
		else if (srvProfesor.insertar(objProfesor) > 0) {
			objRedi.addFlashAttribute("error", MSG_EXS_NMB);
			return "redirect:/profesores/irRegistrar";
		}
		else {
			objRedi.addFlashAttribute("success", MSG_SCS_CRT);
			return "redirect:/profesores/";
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Profesor> objProfesor = srvProfesor.listarPorId(id);
		if (objProfesor == null) {
			objRedir.addFlashAttribute("error", MSG_ERR_EDT);
			return "redirect:/profesores/";
		}
		else {
			model.addAttribute("profesor", objProfesor);
			return "profesor";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id, RedirectAttributes objRedir) {
		try {
			if (id != null && id > 0) {
				srvProfesor.eliminar(id);
				objRedir.addFlashAttribute("success", MSG_SCS_DLT);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			objRedir.addFlashAttribute("error", MSG_ERR_DLT);
		}
		model.put("profesor", new Profesor());
		model.put("listProfesor", srvProfesor.listar());
		return "redirect:/profesores/";
	}
	
	/*@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("profesor", new Profesor());
		model.put("listProfesor", srvProfesor.listar());
		return "listProfesor";
	}
	
	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {
		model.addAttribute("profesor", new Profesor());
		return "searchProfesor";
	}*/
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Profesor profesor) throws ParseException {
		List<Profesor> listProfesor;
		listProfesor = srvProfesor.buscarPorFiltro(profesor.getNombre());
		if (listProfesor.isEmpty()) {
			model.put("info", MSG_NOT_FND);
		}
		model.put("listProfesor", listProfesor);
		return "listProfesor";
	}
}
