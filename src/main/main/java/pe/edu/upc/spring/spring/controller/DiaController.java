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

import pe.edu.upc.spring.model.Dia;
import pe.edu.upc.spring.service.IDiaService;

@Controller
@RequestMapping("/dias")
public class DiaController {
	
	static final String MSG_SCS_CRT = "El día ha sido guardado de manera exitosa.";
	static final String MSG_SCS_EDT = "El día ha sido recuperado de manera exitosa.";
	static final String MSG_SCS_DLT = "El día ha sido eliminado de manera exitosa.";
	static final String MSG_ERR_CRT = "Lo sentimos, ocurrió un error mientras se intentaba guardar el día.";
	static final String MSG_ERR_EDT = "Lo sentimos, ocurrió un error mientras se intentaba recuperar el día.";
	static final String MSG_ERR_DLT = "Lo sentimos, ocurrió un error mientras se intentaba eliminar el día.";
	static final String MSG_EXS_NMB = "El nombre ingresado ya existe en el sistema.";
	static final String MSG_NOT_FND = "No se encontraron coincidencias.";
	
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
	public String registrar(@Valid @ModelAttribute Dia objDia, BindingResult binRes, Model model, RedirectAttributes objRedi) throws ParseException {
		if (binRes.hasErrors())
			return "dia";
		else if (srvDia.insertar(objDia) > 0) {
			objRedi.addFlashAttribute("error", MSG_EXS_NMB);
			return "redirect:/dias/irRegistrar";
		}
		else {
			objRedi.addFlashAttribute("success", MSG_SCS_CRT);
			return "redirect:/dias/";
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Dia> objDia = srvDia.listarPorId(id);
		if (objDia == null) {
			objRedir.addFlashAttribute("error", MSG_ERR_EDT);
			return "redirect:/dias/";
		}
		else {
			model.addAttribute("dia", objDia);
			return "dia";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id") Integer id, RedirectAttributes objRedir) {
		try {
			if (id != null && id > 0) {
				srvDia.eliminar(id);
				objRedir.addFlashAttribute("success", MSG_SCS_DLT);
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			objRedir.addFlashAttribute("error", MSG_ERR_DLT);
		}
		model.put("dia", new Dia());
		model.put("listDia", srvDia.listar());
		return "redirect:/dias/";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Dia dia) throws ParseException {
		List<Dia> listDia;
		listDia = srvDia.buscarPorFiltro(dia.getNombre());
		if (listDia.isEmpty()) {
			model.put("info", MSG_NOT_FND);
		}
		model.put("listDia", listDia);
		return "listDia";
	}
}
