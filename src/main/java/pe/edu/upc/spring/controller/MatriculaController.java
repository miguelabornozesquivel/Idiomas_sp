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

import pe.edu.upc.spring.model.Matricula;
import pe.edu.upc.spring.model.Curso;
import pe.edu.upc.spring.model.Alumno;
import pe.edu.upc.spring.service.IMatriculaService;
import pe.edu.upc.spring.service.ICursoService;
import pe.edu.upc.spring.service.IAlumnoService;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

	@Autowired
	private ICursoService srvCurso;
	
	@Autowired
	private IAlumnoService srvAlumno;
	
	@Autowired
	private IMatriculaService srvMatricula;
	
	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		model.put("matricula", new Matricula());
		model.put("listMatricula", srvMatricula.listar());
		return "listMatricula";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("listCurso", srvCurso.listar());
		model.addAttribute("listAlumno", srvAlumno.listar());
		model.addAttribute("curso", new Curso());
		model.addAttribute("alumno", new Alumno());
		model.addAttribute("matricula", new Matricula());
		return "matricula";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute Matricula objMatricula, BindingResult binRes, Model model) throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listCurso", srvCurso.listar());
			model.addAttribute("listAlumno", srvAlumno.listar());
			return "matricula";
		}
		else {
			boolean flag = srvMatricula.insertar(objMatricula);
			if (flag)
				return "redirect:/matriculas/";
			else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/matriculas/irRegistrar";
			}
		}
		
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Matricula> objMatricula = srvMatricula.listarPorId(id);
		if (objMatricula == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/matriculas/";
		}
		else {
			model.addAttribute("listCurso", srvCurso.listar());
			model.addAttribute("listAlumno", srvAlumno.listar());
			if (objMatricula.isPresent())
				objMatricula.ifPresent(obj -> model.addAttribute("matricula", obj));
			return "matricula";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if (id != null && id > 0) {
				srvMatricula.eliminar(id);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "Ocurrió un error");
		}
		model.put("matricula", new Matricula());
		model.put("listMatricula", srvMatricula.listar());
		return "listMatricula";
	}
	
	@RequestMapping("/mostrar/{id}")
	public String mostrar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Matricula> objMatricula = srvMatricula.listarPorId(id);
		if (objMatricula == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/matriculas/";
		}
		else {
			if (objMatricula.isPresent())
				objMatricula.ifPresent(obj -> model.addAttribute("matricula", obj));
			return "matricula";
		}
	}
	
	/*@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("matricula", new Matricula());
		model.put("listMatricula", srvMatricula.listar());
		return "listMatricula";
	}
	
	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {
		model.addAttribute("matricula", new Matricula());
		return "searchMatricula";
	}*/
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Matricula matricula) throws ParseException {
		List<Matricula> listMatricula;
		listMatricula = srvMatricula.buscarPorFiltro(matricula.getComprobante());
		if (listMatricula.isEmpty()) {
			model.put("mensaje", "No se encontraron coincidencias.");
		}
		model.put("listMatricula", listMatricula);
		return "listMatricula";
	}
}
