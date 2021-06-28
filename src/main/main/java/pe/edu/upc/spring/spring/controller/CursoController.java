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

import pe.edu.upc.spring.model.Curso;
import pe.edu.upc.spring.model.Idioma;
import pe.edu.upc.spring.model.Profesor;
import pe.edu.upc.spring.service.ICursoService;
import pe.edu.upc.spring.service.IIdiomaService;
import pe.edu.upc.spring.service.IProfesorService;

@Controller
@RequestMapping("/cursos")
public class CursoController {

	static final String MSG_SCS_CRT = "El curso ha sido guardado de manera exitosa.";
	static final String MSG_SCS_EDT = "El curso ha sido recuperado de manera exitosa.";
	static final String MSG_SCS_DLT = "El curso ha sido eliminado de manera exitosa.";
	static final String MSG_ERR_CRT = "Lo sentimos, ocurrió un error mientras se intentaba guardar el curso.";
	static final String MSG_ERR_EDT = "Lo sentimos, ocurrió un error mientras se intentaba recuperar el curso.";
	static final String MSG_ERR_DLT = "Lo sentimos, ocurrió un error mientras se intentaba eliminar el curso.";
	static final String MSG_EXS_NMB = "El enlace ingresado ya existe en el sistema.";
	static final String MSG_NOT_FND = "No se encontraron coincidencias.";
	
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
	public String registrar(@Validated({Curso.StepOneVal.class}) @ModelAttribute Curso objCurso, BindingResult binRes, Model model, RedirectAttributes objRedi) throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listIdioma", srvIdioma.listar());
			model.addAttribute("listProfesor", srvProfesor.listar());
			return "curso";
		}
		else if (srvCurso.insertar(objCurso) > 0) {
			objRedi.addFlashAttribute("error", MSG_EXS_NMB);
			return "redirect:/cursos/irRegistrar";
		}
		else {
			objRedi.addFlashAttribute("success", MSG_SCS_CRT);
			return "redirect:/cursos/";
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Curso> objCurso = srvCurso.listarPorId(id);
		if (objCurso == null) {
			objRedir.addFlashAttribute("error", MSG_ERR_EDT);
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
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id, RedirectAttributes objRedir) {
		try {
			if (id != null && id > 0) {
				srvCurso.eliminar(id);
				objRedir.addFlashAttribute("success", MSG_SCS_DLT);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			objRedir.addFlashAttribute("error", MSG_ERR_DLT);
		}
		model.put("curso", new Curso());
		model.put("listCurso", srvCurso.listar());
		return "redirect:/cursos/";
	}
	
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Curso curso) throws ParseException {
		List<Curso> listCurso;
		listCurso = srvCurso.buscarPorFiltro(curso.getNivel());
		if (listCurso.isEmpty()) {
			model.put("info", MSG_NOT_FND);
		}
		model.put("listCurso", listCurso);
		return "listCurso";
	}
}
