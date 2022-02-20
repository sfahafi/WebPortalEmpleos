package com.sfahafi.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sfahafi.model.Perfil;
import com.sfahafi.model.Usuario;
import com.sfahafi.model.Vacante;
import com.sfahafi.repository.I_VacantesRepository;
import com.sfahafi.service.I_CategoriasService;
import com.sfahafi.service.I_UsuariosService;
import com.sfahafi.service.I_VacantesService;

@Controller
public class HomeController {
	
	@Autowired
	private I_VacantesService serviceVacantes;
	
	@Autowired
	private I_UsuariosService ius;
	
	@Autowired
	private I_CategoriasService ics;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas(); // getVacantes usado anteriormente
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	
	
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
	
	@GetMapping("/about")
	public String mostrarAcerca() {			
		return "acerca";
	}
	
	@GetMapping("/login")
	public String mostrarLogin() {
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
		SecurityContextLogoutHandler logoutHandler =
		new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
		
	}
	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		// List<Vacante> lista = serviceVacantes.buscarTodas(); fue reemplazado por el modelo agregado en setGenericos
		// model.addAttribute("vacantes", lista);
		return "home";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		System.out.println("Nombre del usuario: " + username);
		
		for(GrantedAuthority rol : auth.getAuthorities()) {
			System.out.println("ROL: " + rol.getAuthority());
		}
		
		if(session.getAttribute("usuario") == null) {
			Usuario usuario = ius.buscarPorUsername(username);
			usuario.setPassword(null);
			System.out.println("Usuario: " + usuario);
			session.setAttribute("usuario", usuario);
		}
		
		return "redirect:/"; 
	}
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}
	
	@GetMapping("/edituser/{id}")
	public String editarUsuario(@PathVariable("id") int idUsuario, Model model) {
		Usuario usuario = ius.buscarPorId(idUsuario);
		model.addAttribute("usuario", usuario);
		return "formRegistro";
	}
	
		
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {	
		
		usuario.setEstatus(1); // Activado por defecto
		usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor
		
		//Para indicar que la password no esta encriptado utilizamos {noop}+password en la db
		//String pass = usuario.getPassword();
		//usuario.setPassword("{noop}" + pass);
		
		String passPlano = usuario.getPassword();
		String passEncriptado = passwordEncoder.encode(passPlano);
		usuario.setPassword(passEncriptado);
		
		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfil perfil = new Perfil();
		perfil.setId(3); // Perfil USUARIO
		usuario.agregar(perfil);
		
		/**
		 * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
		 */
		ius.guardar(usuario);
				
		attributes.addFlashAttribute("msg", "El usuario se registro con éxito!!");
		
		return "redirect:/usuarios/index";
	}
	
	
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody                        // para redenrizar texto y no una vista
	public String encriptar(@PathVariable("texto") String texto) {
		return texto + " Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
	}
	
	
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("Buscando por: " + vacante);
		
		ExampleMatcher matcher = ExampleMatcher.matching() // equivale a where descripcion like '%?%'
				.withMatcher("descripcion", ExampleMatcher 
				.GenericPropertyMatchers.contains());
		
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		return "home";
	}
	
	/**
	 * InitBinder en el Data Binding, va a settear a null el valor de un String si detecta que viene vacio 
	 * @param binder
	 */
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.reset();
		model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
		model.addAttribute("categorias", ics.buscarTodas());
		model.addAttribute("search", vacanteSearch);
		
	}
	

	
	
	
	
}
