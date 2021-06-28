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

import pe.edu.upc.spring.model.Alumno;
import pe.edu.upc.spring.service.IAlumnoService;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

	static final String MSG_SCS_CRT = "El alumno ha sido guardado de manera exitosa.";
	static final String MSG_SCS_EDT = "El alumno ha sido recuperado de manera exitosa.";
	static final String MSG_SCS_DLT = "El alumno ha sido eliminado de manera exitosa.";
	static final String MSG_ERR_CRT = "Lo sentimos, ocurrió un error mientras se intentaba guardar el alumno.";
	static final String MSG_ERR_EDT = "Lo sentimos, ocurrió un error mientras se intentaba recuperar el alumno.";
	static final String MSG_ERR_DLT = "Lo sentimos, ocurrió un error mientras se intentaba eliminar el alumno.";
	static final String MSG_EXS_NMB = "El correo electrónico ingresado ya existe en el sistema.";
	static final String MSG_NOT_FND = "No se encontraron coincidencias.";
	
	@Autowired
	private IAlumnoService srvAlumno;
	
	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		model.put("alumno", new Alumno());
		model.put("listAlumno", srvAlumno.listar());
		return "listAlumno";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("alumno", new Alumno());
		return "alumno";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@Validated({Alumno.StepOneVal.class}) @ModelAttribute Alumno objAlumno, BindingResult binRes, Model model, RedirectAttributes objRedi) throws ParseException {
		if (binRes.hasErrors())
			return "alumno";
		else if (srvAlumno.insertar(objAlumno) > 0) {
			objRedi.addFlashAttribute("error", MSG_EXS_NMB);
			return "redirect:/alumnos/irRegistrar";
		}
		else {
			objRedi.addFlashAttribute("success", MSG_SCS_CRT);
			return "redirect:/alumnos/";
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Alumno> objAlumno = srvAlumno.listarPorId(id);
		if (objAlumno == null) {
			objRedir.addFlashAttribute("error", MSG_ERR_EDT);
			return "redirect:/alumnos/";
		}
		else {
			model.addAttribute("alumno", objAlumno);
			return "alumno";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id, RedirectAttributes objRedir) {
		try {
			if (id != null && id > 0) {
				srvAlumno.eliminar(id);
				objRedir.addFlashAttribute("success", MSG_SCS_DLT);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			objRedir.addFlashAttribute("error", MSG_ERR_DLT);
		}
		model.put("alumno", new Alumno());
		model.put("listAlumno", srvAlumno.listar());
		return "redirect:/alumnos/";
	}
	
	/*@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("alumno", new Alumno());
		model.put("listAlumno", srvAlumno.listar());
		return "listAlumno";
	}
	
	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {
		model.addAttribute("alumno", new Alumno());
		return "searchAlumno";
	}*/
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Alumno alumno) throws ParseException {
		List<Alumno> listAlumno;
		listAlumno = srvAlumno.buscarPorFiltro(alumno.getNombre());
		if (listAlumno.isEmpty()) {
			model.put("info", MSG_NOT_FND);
		}
		model.put("listAlumno", listAlumno);
		return "listAlumno";
	}
}
