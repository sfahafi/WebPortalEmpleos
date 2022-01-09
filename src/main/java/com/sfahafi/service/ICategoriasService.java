package com.sfahafi.service;

import java.util.List;

import com.sfahafi.model.Categoria;

public interface ICategoriasService {

	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);
	
}
