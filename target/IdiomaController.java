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

import pe.edu.upc.spring.model.Idioma;
import pe.edu.upc.spring.service.IIdiomaService;

@Controller
@RequestMapping("/idiomas")
public class IdiomaController {

	@Autowired
	private IIdiomaService srvIdioma;
	
	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		model.put("idioma", new Idioma());
		model.put("listIdioma", srvIdioma.listar());
		return "listIdioma";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("idioma", new Idioma());
		return "idioma";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute Idioma objIdioma, BindingResult binRes, Model model) throws ParseException {
		if (binRes.hasErrors())
			return "idioma";
		else {
			boolean flag = srvIdioma.insertar(objIdioma);
			if (flag)
				return "redirect:/idiomas/";
			else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/idiomas/irRegistrar";
			}
		}
		
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Idioma> objIdioma = srvIdioma.listarPorId(id);
		if (objIdioma == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/idiomas/";
		}
		else {
			model.addAttribute("idioma", objIdioma);
			return "idioma";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if (id != null && id > 0) {
				srvIdioma.eliminar(id);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "Ocurrió un error");
		}
		model.put("idioma", new Idioma());
		model.put("listIdioma", srvIdioma.listar());
		return "listIdioma";
	}
	
	/*@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("idioma", new Idioma());
		model.put("listIdioma", srvIdioma.listar());
		return "listIdioma";
	}
	
	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {
		model.addAttribute("idioma", new Idioma());
		return "searchIdioma";
	}*/
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Idioma idioma) throws ParseException {
		List<Idioma> listIdioma;
		listIdioma = srvIdioma.buscarPorNombre(idioma.getNombre());
		if (listIdioma.isEmpty()) {
			model.put("mensaje", "No se encontraron coincidencias.");
		}
		model.put("listIdioma", listIdioma);
		return "listIdioma";
	}
}
