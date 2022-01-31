package com.sfahafi.service;

import java.util.List;

import com.sfahafi.model.Usuario;

public interface I_UsuariosService {

	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	List<Usuario> buscarTodos();
	Usuario buscarPorId(Integer idUsuario);
	
}
