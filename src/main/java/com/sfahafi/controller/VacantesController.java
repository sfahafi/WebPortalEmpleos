package com.sfahafi.controller;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sfahafi.model.Vacante;
import com.sfahafi.service.I_CategoriasService;
import com.sfahafi.service.I_VacantesService;
import com.sfahafi.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

	@Value("${rutasaveimg}")
	private String ruta;

	@Autowired
	private I_VacantesService serviceVacantes;

	@Autowired
	private I_CategoriasService serviceCategorias;

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = serviceVacantes.buscarTodas(page);
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}

	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		// model.addAttribute("categorias", serviceCategorias.buscarTodas()); // esta en
		// @ModelAttribute
		return "vacantes/formVacante";
	}

	// Metodo save utilizando Data Binding
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes,
			@RequestParam("archivoImagen") MultipartFile multiPart) { // BindingResult captura el error en la bariable
																		// result
		if (result.hasErrors()) { // Si se detecta error, vuelve al formulario de vacante e imprime el error en
									// consola
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}

		if (!multiPart.isEmpty()) {
			// String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			// String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta); // la ruta se guardo en app.properties y se
																			// inyecto con la anotacio @Value
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}

		serviceVacantes.guardar(vacante);
		attributes.addFlashAttribute("msg", "Registro Guardado");
		System.out.println("Vacante: " + vacante);

		return "redirect:/vacantes/index";
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	/*
	 * @PostMapping("/save") public String guardar(@RequestParam("nombre") String
	 * nombre, @RequestParam("descripcion") String descripcion,
	 * 
	 * @RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha,
	 * 
	 * @RequestParam("destacado") int destacado, @RequestParam("salario") Double
	 * salario,
	 * 
	 * @RequestParam("detalles") String detalles){
	 * 
	 * System.out.println("Nombre de Vacante: " + nombre);
	 * System.out.println("Descripcion: " + descripcion);
	 * System.out.println("Estatus: " + estatus);
	 * System.out.println("Fecha de Publicacion: " + fecha);
	 * System.out.println("Destacado: " + destacado); System.out.println("Salario: "
	 * + salario); System.out.println("Detalles: " + detalles);
	 * 
	 * return "vacantes/listVacantes"; }
	 */

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {
		System.out.println("Borrando vacante con id: " + idVacante);

		try {

			serviceVacantes.eliminar(idVacante);
			attributes.addFlashAttribute("msg", "La vacante fue eliminada"); // para desplegar mensaje en la vista
			// model.addAttribute("id", idVacante);
			
		} catch (Exception ex) {
			attributes.addFlashAttribute("msg", "No es posible eliminar la Categoría seleccionada!.");
		}
		
		return "redirect:/vacantes/index";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		// model.addAttribute("categorias", serviceCategorias.buscarTodas()); // esta en
		// @ModelAttribute
		return "vacantes/formVacante";
	}

	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
	}

	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {

		Vacante vacante = serviceVacantes.buscarPorId(idVacante);

		System.out.println("vacante" + vacante);
		model.addAttribute("vacante", vacante);
		return "detalle";
	}

}
