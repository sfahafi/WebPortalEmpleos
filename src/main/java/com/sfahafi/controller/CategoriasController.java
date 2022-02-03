package com.sfahafi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sfahafi.model.Categoria;
import com.sfahafi.service.I_CategoriasService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {

	@Autowired
	//@Qualifier("categoriasServiceJpa)" -- Para definir cual de las interfaces se va a implementar en caso de no usar @Primary en la interfaz
	private I_CategoriasService ics;
	
	@GetMapping("/index")
	// @RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) {
		List<Categoria> lista = ics.buscarTodas();
    	model.addAttribute("categorias", lista);

		return "categorias/listCategorias";
	}
	
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado2(Model model, Pageable page) {
		Page<Categoria> lista = ics.buscartodas(page);
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}
	
	@GetMapping("/create")
	//@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategorias";
	}
	
	@PostMapping("/save")
	//@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()){		
			System.out.println("Existieron errores");
			return "categorias/formCategoria";
		}	
		
		// Guadamos el objeto categoria en la bd
		ics.guardar(categoria);
		attributes.addFlashAttribute("msg", "Los datos de la categorÃ­a fueron guardados!");		
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {
		System.out.println("Borrando la categoria con id: " + idCategoria);
		ics.eliminar(idCategoria);
		attributes.addFlashAttribute("msg", "La Categoria fue eliminada!");
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model) {
		Categoria categoria = ics.buscarPorId(idCategoria);
		model.addAttribute("categoria", categoria);
		return "categorias/formCategorias";
	}
	
	/*
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", ics.buscarTodas());
	}*/

	/*
	// @PostMapping("/save")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion) {
		System.out.println("Categoria: " + nombre);
		System.out.println("Descripcion: " + descripcion);
		return "categorias/listCategorias";
	} */
	
}
