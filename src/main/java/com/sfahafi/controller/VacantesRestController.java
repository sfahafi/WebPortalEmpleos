package com.sfahafi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping(value="/vacantes", produces="application/json")
	public ResponseEntity <List<Vacante>> buscarTodas() {
		return new ResponseEntity <List<Vacante>> (ivs.buscarTodas(), HttpStatus.OK);
	}
	
	/**
	 * Metodo para buscar por id
	 * @param id
	 * @return
	 */
	@GetMapping(value="/vacantes/{id}", produces="application/json")
	public ResponseEntity <Vacante>  buscarPorId(@PathVariable("id") int id) {
		return new ResponseEntity <Vacante> (ivs.buscarPorId(id), HttpStatus.OK);
	}
	
	
	@PostMapping(value="/vacantes", produces="application/json")
	public ResponseEntity<Vacante> guardar(@RequestBody Vacante vacante) {
	//public ResponseEntity<?> guardar(@RequestBody Vacante vacante) {
		ivs.guardar(vacante);
		return new ResponseEntity<Vacante>(vacante,null,HttpStatus.CREATED);
		//return new ResponseEntity<CustomSuccess>(new CustomSuccess("Album creado"), HttpStatus.OK);
	}

	/*// forma basica
	@PostMapping("/vacantes")
	public Vacante guardar(@RequestBody Vacante vacante) {
		ivs.guardar(vacante);
		return vacante;
	}*/
	
	@PutMapping(value="/vacantes", produces="application/json")
	public ResponseEntity<Vacante> modificar(@RequestBody Vacante vacante) {
		ivs.guardar(vacante);
		return new ResponseEntity <Vacante> (vacante, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/vacantes/{id}")
	public String eliminar(@PathVariable("id") int id) {
	//public ResponseEntity<?> eliminar(@PathVariable("id") int id) {
		//Vacante vacante = ivs.buscarPorId(id);
//		if(vacante == null) {
//			return new ResponseEntity<CustomError>(new CustomError("No existe la vacante con id: " + id), HttpStatus.NOT_FOUND);
//		}
		ivs.eliminar(id);
		return "Registro eliminado!";
		//return new ResponseEntity<CustomSuccess>(new CustomSuccess("La vacante fue eliminada!!"), HttpStatus.OK);
	}
	
}
