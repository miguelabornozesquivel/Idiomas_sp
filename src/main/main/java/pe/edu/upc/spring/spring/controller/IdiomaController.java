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

import pe.edu.upc.spring.model.Idioma;
import pe.edu.upc.spring.service.IIdiomaService;

@Controller
@RequestMapping("/idiomas")
public class IdiomaController {
	
	static final String MSG_SCS_CRT = "El idioma ha sido guardado de manera exitosa.";
	static final String MSG_SCS_EDT = "El idioma ha sido recuperado de manera exitosa.";
	static final String MSG_SCS_DLT = "El idioma ha sido eliminado de manera exitosa.";
	static final String MSG_ERR_CRT = "Lo sentimos, ocurrió un error mientras se intentaba guardar el idioma.";
	static final String MSG_ERR_EDT = "Lo sentimos, ocurrió un error mientras se intentaba recuperar el idioma.";
	static final String MSG_ERR_DLT = "Lo sentimos, ocurrió un error mientras se intentaba eliminar el idioma.";
	static final String MSG_EXS_NMB = "El nombre ingresado ya existe en el sistema.";
	static final String MSG_NOT_FND = "No se encontraron coincidencias.";
	
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
	public String registrar(@Valid @ModelAttribute Idioma objIdioma, BindingResult binRes, Model model, RedirectAttributes objRedi) throws ParseException {
		if (binRes.hasErrors())
			return "idioma";
		else if (srvIdioma.insertar(objIdioma) > 0) {
			objRedi.addFlashAttribute("error", MSG_EXS_NMB);
			return "redirect:/idiomas/irRegistrar";
		}
		else {
			objRedi.addFlashAttribute("success", MSG_SCS_CRT);
			return "redirect:/idiomas/";
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Idioma> objIdioma = srvIdioma.listarPorId(id);
		if (objIdioma == null) {
			objRedir.addFlashAttribute("error", MSG_ERR_EDT);
			return "redirect:/idiomas/";
		}
		else {
			model.addAttribute("idioma", objIdioma);
			return "idioma";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id, RedirectAttributes objRedir) {
		try {
			if (id != null && id > 0) {
				srvIdioma.eliminar(id);
				objRedir.addFlashAttribute("success", MSG_SCS_DLT);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			objRedir.addFlashAttribute("error", MSG_ERR_DLT);
		}
		model.put("idioma", new Idioma());
		model.put("listIdioma", srvIdioma.listar());
		return "redirect:/idiomas/";
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
		listIdioma = srvIdioma.buscarPorFiltro(idioma.getNombre());
		if (listIdioma.isEmpty()) {
			model.put("info", MSG_NOT_FND);
		}
		model.put("listIdioma", listIdioma);
		return "listIdioma";
	}
}
