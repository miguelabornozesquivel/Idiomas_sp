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

import pe.edu.upc.spring.model.Sesion;
import pe.edu.upc.spring.model.Curso;
import pe.edu.upc.spring.model.Dia;
import pe.edu.upc.spring.service.ISesionService;
import pe.edu.upc.spring.service.ICursoService;
import pe.edu.upc.spring.service.IDiaService;

@Controller
@RequestMapping("/sesiones")
public class SesionController {

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
	public String registrar(@ModelAttribute Sesion objSesion, BindingResult binRes, Model model) throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listCurso", srvCurso.listar());
			model.addAttribute("listDia", srvDia.listar());
			return "sesion";
		}
		else {
			boolean flag = srvSesion.insertar(objSesion);
			if (flag)
				return "redirect:/sesiones/";
			else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/sesiones/irRegistrar";
			}
		}
		
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Sesion> objSesion = srvSesion.listarPorId(id);
		if (objSesion == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
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
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if (id != null && id > 0) {
				srvSesion.eliminar(id);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "Ocurrió un error");
		}
		model.put("sesion", new Sesion());
		model.put("listSesion", srvSesion.listar());
		return "listSesion";
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
			model.put("mensaje", "No se encontraron coincidencias.");
		}
		model.put("listSesion", listSesion);
		return "listSesion";
	}
}
