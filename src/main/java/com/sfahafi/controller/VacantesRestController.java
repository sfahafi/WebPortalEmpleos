package com.sfahafi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sfahafi.model.Vacante;
import com.sfahafi.service.I_VacantesService;


@RestController
@RequestMapping("/api/rest")
public class VacantesRestController {

	@Autowired
	private I_VacantesService ivs;
	
	@GetMapping("vacantes")
	public List<Vacante> buscarTodas() {
		return ivs.buscarTodas();
	}
	
	@PostMapping("/vacantes")
	public Vacante guardar(@RequestBody Vacante vacante) {
		ivs.guardar(vacante);
		return vacante;
	}
	
	@PutMapping("/vacantes")
	public Vacante modificar(@RequestBody Vacante vacante) {
		ivs.guardar(vacante);
		return vacante;
	}
	
	@DeleteMapping("/vacantes/{id}")
	public String eliminar(@PathVariable("id") int vacante) {
		ivs.eliminar(vacante);
		return "Registro eliminado!";
	}
	
}
