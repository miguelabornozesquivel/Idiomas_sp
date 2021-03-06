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

import pe.edu.upc.spring.model.Curso;
import pe.edu.upc.spring.model.Idioma;
import pe.edu.upc.spring.model.Profesor;
import pe.edu.upc.spring.service.ICursoService;
import pe.edu.upc.spring.service.IIdiomaService;
import pe.edu.upc.spring.service.IProfesorService;

@Controller
@RequestMapping("/cursos")
public class CursoController {

	@Autowired
	private IIdiomaService srvIdioma;
	
	@Autowired
	private IProfesorService srvProfesor;
	
	@Autowired
	private ICursoService srvCurso;
	
	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		model.put("curso", new Curso());
		model.put("listCurso", srvCurso.listar());
		return "listCurso";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("listIdioma", srvIdioma.listar());
		model.addAttribute("listProfesor", srvProfesor.listar());
		model.addAttribute("idioma", new Idioma());
		model.addAttribute("profesor", new Profesor());
		model.addAttribute("curso", new Curso());
		return "curso";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute Curso objCurso, BindingResult binRes, Model model) throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listIdioma", srvIdioma.listar());
			model.addAttribute("listProfesor", srvProfesor.listar());
			return "curso";
		}
		else {
			boolean flag = srvCurso.insertar(objCurso);
			if (flag)
				return "redirect:/cursos/";
			else {
				model.addAttribute("mensaje", "Ocurri?? un error");
				return "redirect:/cursos/irRegistrar";
			}
		}
		
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Curso> objCurso = srvCurso.listarPorId(id);
		if (objCurso == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurri?? un error");
			return "redirect:/cursos/";
		}
		else {
			model.addAttribute("listIdioma", srvIdioma.listar());
			model.addAttribute("listProfesor", srvProfesor.listar());
			if (objCurso.isPresent())
				objCurso.ifPresent(obj -> model.addAttribute("curso", obj));
			return "curso";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if (id != null && id > 0) {
				srvCurso.eliminar(id);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "Ocurri?? un error");
		}
		model.put("curso", new Curso());
		model.put("listCurso", srvCurso.listar());
		return "listCurso";
	}
	
	/*@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("curso", new Curso());
		model.put("listCurso", srvCurso.listar());
		return "listCurso";
	}
	
	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {
		model.addAttribute("curso", new Curso());
		return "searchCurso";
	}*/
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Curso curso) throws ParseException {
		List<Curso> listCurso;
		listCurso = srvCurso.buscarPorFiltro(curso.getNivel());
		if (listCurso.isEmpty()) {
			model.put("mensaje", "No se encontraron coincidencias.");
		}
		model.put("listCurso", listCurso);
		return "listCurso";
	}
}
