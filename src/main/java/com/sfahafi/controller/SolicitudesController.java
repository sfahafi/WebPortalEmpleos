package com.sfahafi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sfahafi.model.Solicitud;
import com.sfahafi.model.Usuario;
import com.sfahafi.model.Vacante;
import com.sfahafi.service.I_SolicitudesService;
import com.sfahafi.service.I_UsuariosService;
import com.sfahafi.service.I_VacantesService;
import com.sfahafi.util.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {

	
	@Value("${empleosapp.ruta.cv}")
	private String ruta;
	
	// Inyectamos una instancia desde nuestro ApplicationContext
    @Autowired
	private I_SolicitudesService iss;

    // Inyectamos una instancia desde nuestro ApplicationContext
    @Autowired
	private I_VacantesService ivs;
    
    @Autowired
   	private I_UsuariosService ius;
	
    
    // Metodo que muestra la lista de solicitudes sin paginacion
    @GetMapping("/index")
    public String mostrarIndex(Model model) {
    	List<Solicitud> lista = iss.buscarTodas();
    	model.addAttribute("solicitudes", lista);
    	return "solicitudes/listSolicitudes";
    }
    
    // Método para renderizar el formulario de solicitud a una Vacante
    @GetMapping("/create/{idVacante}")
    public String crear(Solicitud solicitud, @PathVariable Integer idVacante, Model model) {
    	Vacante vacante = ivs.buscarPorId(idVacante);
    	model.addAttribute("vacante", vacante);
    	return "solicitudes/formSolicitudes";
    }
    
    // Método para guardar la solicitud enviada por el usuario en la base de datos
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, Model model, HttpSession session,
			@RequestParam("archivoCV") MultipartFile multiPart, RedirectAttributes attributes, Authentication authentication) {
		
		// Recuperamos el username que inicio sesión
				String username = authentication.getName();
				
				if (result.hasErrors()){
					
					System.out.println("Existieron errores");
					return "solicitudes/formSolicitud";
				}	
				
				if (!multiPart.isEmpty()) {
					//String ruta = "/empleos/files-cv/"; // Linux/MAC
					//String ruta = "c:/empleos/files-cv/"; // Windows
					String nombreArchivo = Utileria.guardarArchivo(multiPart, ruta);
					if (nombreArchivo!=null){ // El archivo (CV) si se subio				
						solicitud.setArchivo(nombreArchivo); // Asignamos el nombre de la imagen
					}	
				}

				// Buscamos el objeto Usuario en BD	
				Usuario usuario = ius.buscarPorUsername(username);		
				
				solicitud.setUsuario(usuario); // Referenciamos la solicitud con el usuario 
				solicitud.setFecha(new Date());
				// Guadamos el objeto solicitud en la bd
				iss.guardar(solicitud);
				attributes.addFlashAttribute("msg", "Gracias por enviar tu CV!");
					
				//return "redirect:/solicitudes/index";
				return "redirect:/";
		
	}
	
	// Método para eliminar una solicitud
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes attributes) {
		iss.eliminar(idSolicitud);
		
		attributes.addFlashAttribute("msg", "La solicitud fue eliminada!.");
		//return "redirect:/solicitudes/index";
		return "redirect:/solicitudes/indexPaginate";
	}
	
	
	
	//Personalizamos el Data Binding para todas las propiedades de tipo Date
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
