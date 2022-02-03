package com.sfahafi.service;

import java.util.List;

import org.springframework.data.domain.Example;

import com.sfahafi.model.Vacante;

public interface I_VacantesService {
	
	List<Vacante> buscarTodas();	
	Vacante buscarPorId(Integer idVacante);
	void guardar(Vacante vacante);
	List<Vacante> buscarDestacadas();
	void eliminar(Integer idVacante);
	List<Vacante> buscarByExample(Example<Vacante> example);

	
}
