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

import pe.edu.upc.spring.model.Alumno;
import pe.edu.upc.spring.service.IAlumnoService;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {

	@Autowired
	private IAlumnoService srvAlumno;
	
	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		model.put("listAlumno", srvAlumno.listar());
		return "listAlumno";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("alumno", new Alumno());
		return "alumno";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute Alumno objAlumno, BindingResult binRes, Model model) throws ParseException {
		if (binRes.hasErrors())
			return "alumno";
		else {
			boolean flag = srvAlumno.insertar(objAlumno);
			if (flag)
				return "redirect:/alumno/listar";
			else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/alumno/irRegistrar";
			}
		}
		
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Alumno> objAlumno = srvAlumno.listarPorId(id);
		if (objAlumno == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/alumno/listar";
		}
		else {
			model.addAttribute("alumno", objAlumno);
			return "alumno";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if (id != null && id > 0) {
				srvAlumno.eliminar(id);
				model.put("listAlumno", srvAlumno.listar());
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "Ocurrió un error");
			model.put("listAlumno", srvAlumno.listar());
		}
		return "listAlumno";
	}
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listAlumno", srvAlumno.listar());
		return "listAlumno";
	}
	
	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {
		model.addAttribute("alumno", new Alumno());
		return "searchAlumno";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Alumno alumno) throws ParseException {
		List<Alumno> listAlumno;
		listAlumno = srvAlumno.buscarPorNombre(alumno.getNombre());
		if (listAlumno.isEmpty()) {
			model.put("mensaje", "No se encontraron coincidencias.");
		}
		model.put("listAlumno", listAlumno);
		return "searchAlumno";
	}
}
