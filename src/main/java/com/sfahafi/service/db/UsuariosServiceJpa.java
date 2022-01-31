package com.sfahafi.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfahafi.model.Usuario;
import com.sfahafi.repository.I_UsuariosRepository;
import com.sfahafi.service.I_UsuariosService;

@Service
public class UsuariosServiceJpa implements I_UsuariosService {

	@Autowired
	private I_UsuariosRepository ur;
	
	@Override
	public void guardar(Usuario usuario) {
		ur.save(usuario);

	}

	@Override
	public void eliminar(Integer idUsuario) {
		ur.deleteById(idUsuario);

	}

	@Override
	public List<Usuario> buscarTodos() {
		return ur.findAll();
	}

}
