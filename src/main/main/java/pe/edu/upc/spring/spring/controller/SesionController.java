package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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

import pe.edu.upc.spring.model.Sesion;
import pe.edu.upc.spring.model.Curso;
import pe.edu.upc.spring.model.Dia;
import pe.edu.upc.spring.service.ISesionService;
import pe.edu.upc.spring.service.ICursoService;
import pe.edu.upc.spring.service.IDiaService;

@Controller
@RequestMapping("/sesiones")
public class SesionController {
	
	static final String MSG_SCS_CRT = "La sesión ha sido guardado de manera exitosa.";
	static final String MSG_SCS_EDT = "La sesión ha sido recuperado de manera exitosa.";
	static final String MSG_SCS_DLT = "La sesión ha sido eliminado de manera exitosa.";
	static final String MSG_ERR_CRT = "Lo sentimos, ocurrió un error mientras se intentaba guardar la sesión.";
	static final String MSG_ERR_EDT = "Lo sentimos, ocurrió un error mientras se intentaba recuperar la sesión.";
	static final String MSG_ERR_DLT = "Lo sentimos, ocurrió un error mientras se intentaba eliminar la sesión.";
	static final String MSG_EXS_NMB = "Existe un cruce de horas para el curso y día ingresados.";
	static final String MSG_ERR_HOR = "La hora de inicio debe ser posterior a la fecha de término.";
	static final String MSG_NOT_FND = "No se encontraron coincidencias.";
	
	@Autowired
	private ICursoService srvCurso;
	
	@Autowired
	private IDiaService srvDia;
	
	@Autowired
	private ISesionService srvSesion;
	
	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		model.put("sesion", new Sesion());
		model.put("listSesion", srvSesion.listar());
		return "listSesion";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("listCurso", srvCurso.listar());
		model.addAttribute("listDia", srvDia.listar());
		model.addAttribute("curso", new Curso());
		model.addAttribute("dia", new Dia());
		model.addAttribute("sesion", new Sesion());
		return "sesion";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@Valid @ModelAttribute Sesion objSesion, BindingResult binRes, Model model, RedirectAttributes objRedi) throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listCurso", srvCurso.listar());
			model.addAttribute("listDia", srvDia.listar());
			return "sesion";
		}
		else if (objSesion.getHoraFin().compareTo(objSesion.getHoraIni()) <= 0) {
			objRedi.addFlashAttribute("error", MSG_ERR_HOR);
			return "redirect:/sesiones/irRegistrar";
		}
		else if (srvSesion.insertar(objSesion) > 0) {
			objRedi.addFlashAttribute("error", MSG_EXS_NMB);
			return "redirect:/sesiones/irRegistrar";
		}
		else {
			objRedi.addFlashAttribute("success", MSG_SCS_CRT);
			return "redirect:/sesiones/";
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Sesion> objSesion = srvSesion.listarPorId(id);
		if (objSesion == null) {
			objRedir.addFlashAttribute("error", MSG_ERR_EDT);
			return "redirect:/sesiones/";
		}
		else {
			model.addAttribute("listCurso", srvCurso.listar());
			model.addAttribute("listDia", srvDia.listar());
			if (objSesion.isPresent())
				objSesion.ifPresent(obj -> model.addAttribute("sesion", obj));
			return "sesion";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id, RedirectAttributes objRedir) {
		try {
			if (id != null && id > 0) {
				srvSesion.eliminar(id);
				objRedir.addFlashAttribute("success", MSG_SCS_DLT);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			objRedir.addFlashAttribute("error", MSG_ERR_DLT);
		}
		model.put("sesion", new Sesion());
		model.put("listSesion", srvSesion.listar());
		return "redirect:/sesiones/";
	}
	
	/*@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("sesion", new Sesion());
		model.put("listSesion", srvSesion.listar());
		return "listSesion";
	}
	
	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {
		model.addAttribute("sesion", new Sesion());
		return "searchSesion";
	}*/
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Sesion sesion) throws ParseException {
		List<Sesion> listSesion;
		listSesion = srvSesion.buscarPorFiltro(sesion.getDetalle());
		if (listSesion.isEmpty()) {
			model.put("info", MSG_NOT_FND);
		}
		model.put("listSesion", listSesion);
		return "listSesion";
	}
}
