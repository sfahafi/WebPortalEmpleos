package com.sfahafi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sfahafi.model.Categoria;

public interface I_CategoriasService {

	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);
	void eliminar(Integer idCategoria);
	Page<Categoria> buscartodas(Pageable page);
}
