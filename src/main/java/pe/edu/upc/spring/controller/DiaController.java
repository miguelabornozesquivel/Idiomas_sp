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

import pe.edu.upc.spring.model.Dia;
import pe.edu.upc.spring.service.IDiaService;

@Controller
@RequestMapping("/dias")
public class DiaController {

	@Autowired
	private IDiaService srvDia;

	@RequestMapping("/")
	public String irListado(Map<String, Object> model) {
		model.put("dia", new Dia());
		model.put("listDia", srvDia.listar());
		return "listDia";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("dia", new Dia());
		return "dia";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute Dia objDia, BindingResult binRes, Model model) throws ParseException {
		if (binRes.hasErrors())
			return "dia";
		else {
			boolean flag = srvDia.insertar(objDia);
			if (flag)
				return "redirect:/dias/";
			else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/dias/irRegistrar";
			}
		}
		
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Dia> objDia = srvDia.listarPorId(id);
		if (objDia == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/dias/";
		}
		else {
			model.addAttribute("dia", objDia);
			return "dia";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if (id != null && id > 0) {
				srvDia.eliminar(id);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "Ocurrió un error");
		}
		model.put("dia", new Dia());
		model.put("listDia", srvDia.listar());
		return "listDia";
	}
	
	/*@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("dia", new Dia());
		model.put("listDia", srvDia.listar());
		return "listDia";
	}
	
	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {
		model.addAttribute("dia", new Dia());
		return "searchDia";
	}*/
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Dia dia) throws ParseException {
		List<Dia> listDia;
		listDia = srvDia.buscarPorNombre(dia.getNombre());
		if (listDia.isEmpty()) {
			model.put("mensaje", "No se encontraron coincidencias.");
		}
		model.put("listDia", listDia);
		return "listDia";
	}
}
