package com.sfahafi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	
}
