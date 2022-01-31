package com.sfahafi.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sfahafi.model.Perfil;
import com.sfahafi.model.Usuario;
import com.sfahafi.model.Vacante;
import com.sfahafi.service.I_UsuariosService;
import com.sfahafi.service.I_VacantesService;

@Controller
public class HomeController {
	
	@Autowired
	private I_VacantesService serviceVacantes;
	
	private I_UsuariosService ius;
	
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas(); // getVacantes usado anteriormente
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	
//	@GetMapping("/detalle")
//	public String mostrarDetalle(Model model) {
//		Vacante vacante = new Vacante();
//		vacante.setNombre("Ingeniero de comunicaciones");
//		vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
//		vacante.setFecha(new Date());
//		vacante.setSalario(9700.0);
//		model.addAttribute("vacante", vacante);
//		return "detalle";
//	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de contabilidad");
		lista.add("vendedor");
		lista.add("Arquitecto");
		
		model.addAttribute("empleos", lista);
		
		return "listado";
		
	}
	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		// List<Vacante> lista = serviceVacantes.buscarTodas(); fue reemplazado por el modelo agregado en setGenericos
		// model.addAttribute("vacantes", lista);
		return "home";
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		usuario.setEstatus(1); // Activado por defecto
		usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor
		
		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfil perfil = new Perfil();
		perfil.setId(3); // Perfil USUARIO
		usuario.agregar(perfil);
		
		/**
		 * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
		 */
		ius.guardar(usuario);
				
		attributes.addFlashAttribute("msg", "El registro fue guardado correctamente!");
		
		return "redirect:/usuarios/index";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
		
	}
	
	
	//***********************************************************************************************
	
	/**
	 * Metodo que regresa una lista de objetos de tipo Vacante
	 * @return
	 */
	
	
	/*
	private List<Vacante> getVacantes(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<Vacante> lista = new LinkedList<Vacante>();
		try {
			
			// Se crea la primera vacante
			Vacante vacante1 = new Vacante();
			vacante1.setId(1);
			vacante1.setNombre("Ingeniero Civil"); // Titulo de la vacante
			vacante1.setDescripcion("Solicitamos Ing. Civil para disenar puente peatonal");
			vacante1.setFecha(sdf.parse("08-02-2021"));
			vacante1.setSalario(10000.0);
			vacante1.setDestacado(1);
			vacante1.setImagen("empresa1.png");
			
			// Se crea la segunda vacante
			Vacante vacante2 = new Vacante();
			vacante2.setId(2);
			vacante2.setNombre("Contador Publico"); 
			vacante2.setDescripcion("Empresa importante solicita contador con 5 anos de experiencia");
			vacante2.setFecha(sdf.parse("09-02-2021"));
			vacante2.setSalario(120000.0);
			vacante2.setDestacado(0);
			vacante2.setImagen("empresa2.png");
			
			// Se crea la tercera vacante
			Vacante vacante3 = new Vacante();
			vacante3.setId(3);
			vacante3.setNombre("Ingeniero Electronico"); 
			vacante3.setDescripcion("Solicitamos Ing. electronico para importante empresa");
			vacante3.setFecha(sdf.parse("06-04-2021"));
			vacante3.setSalario(8000.0);
			vacante3.setDestacado(0);
			
			// Se crea la cuarta vacante
			Vacante vacante4 = new Vacante();
			vacante4.setId(4);
			vacante4.setNombre("Disenador grafico"); 
			vacante4.setDescripcion("Solicitamos Ing. Civil para disenar puente peatonal");
			vacante4.setFecha(sdf.parse("05-05-2021"));
			vacante4.setSalario(10304.0);
			vacante4.setDestacado(1);
			vacante4.setImagen("empresa4.png");
			
			
			Se agregan los cuatro objetos de tipo Vacante a la lista
			 
			
			lista.add(vacante1);
			lista.add(vacante2);
			lista.add(vacante3);
			lista.add(vacante4);
			
		} catch (ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return lista;
	}*/
	
	
	
	
}
