package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	static final String MSG_SCS_CRT = "La matrícula ha sido guardado de manera exitosa.";
	static final String MSG_SCS_EDT = "La matrícula ha sido recuperado de manera exitosa.";
	static final String MSG_SCS_DLT = "La matrícula ha sido eliminado de manera exitosa.";
	static final String MSG_ERR_CRT = "Lo sentimos, ocurrió un error mientras se intentaba guardar la matrícula.";
	static final String MSG_ERR_EDT = "Lo sentimos, ocurrió un error mientras se intentaba recuperar la matrícula.";
	static final String MSG_ERR_DLT = "Lo sentimos, ocurrió un error mientras se intentaba eliminar la matrícula.";
	static final String MSG_EXS_NMB = "El alumno ya se encuentra matriculado en el curso seleccionado.";
	static final String MSG_NOT_FND = "No se encontraron coincidencias.";
	
	@Autowired
	private ICursoService srvCurso;
	
	@Autowired
	private IAlumnoService srvAlumno;
	
	@Autowired
	private IMatriculaService srvMatricula;
		
	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		/* Obtenemos el usuario logueado */
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String correo;
		if (principal instanceof UserDetails) {
			correo = ((UserDetails)principal).getUsername();
		} else {
			correo = principal.toString();
		}
		/* Llenado de la lista de alumnos */
		if (correo.compareToIgnoreCase("admin@gmail.com") == 0) {
			model.put("listMatricula", srvMatricula.listar());
		}
		else {
			model.put("listMatricula", srvMatricula.buscarPorCorreo(correo));
		}
		model.put("matricula", new Matricula());
		
		return "listMatricula";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		/* Obtenemos el usuario logueado */
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String correo;
		if (principal instanceof UserDetails) {
			correo = ((UserDetails)principal).getUsername();
		} else {
			correo = principal.toString();
		}
		/* Llenado de la lista de alumnos */
		if (correo.compareToIgnoreCase("admin@gmail.com") == 0) {
			model.addAttribute("listAlumno", srvAlumno.listar());
		}
		else {
			model.addAttribute("listAlumno", srvAlumno.buscarPorCorreo(correo).get());
		}
		model.addAttribute("listCurso", srvCurso.listar());
		model.addAttribute("curso", new Curso());
		model.addAttribute("alumno", new Alumno());
		model.addAttribute("matricula", new Matricula());
		return "matricula";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@Valid @ModelAttribute Matricula objMatricula, BindingResult binRes, Model model, RedirectAttributes objRedi) throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listCurso", srvCurso.listar());
			model.addAttribute("listAlumno", srvAlumno.listar());
			return "matricula";
		}
		else if (srvMatricula.insertar(objMatricula) > 0) {
			objRedi.addFlashAttribute("error", MSG_EXS_NMB);
			return "redirect:/matriculas/irRegistrar";
		}
		else {
			objRedi.addFlashAttribute("success", MSG_SCS_CRT);
			return "redirect:/matriculas/";
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Matricula> objMatricula = srvMatricula.listarPorId(id);
		if (objMatricula == null) {
			objRedir.addFlashAttribute("error", MSG_ERR_EDT);
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
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id, RedirectAttributes objRedir) {
		try {
			if (id != null && id > 0) {
				srvMatricula.eliminar(id);
				objRedir.addFlashAttribute("success", MSG_SCS_DLT);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			objRedir.addFlashAttribute("error", MSG_ERR_DLT);
		}
		model.put("matricula", new Matricula());
		model.put("listMatricula", srvMatricula.listar());
		return "redirect:/matriculas/";
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
			model.put("info", MSG_NOT_FND);
		}
		model.put("listMatricula", listMatricula);
		return "listMatricula";
	}
}
