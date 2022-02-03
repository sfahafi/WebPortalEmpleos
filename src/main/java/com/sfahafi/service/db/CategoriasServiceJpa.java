package com.sfahafi.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sfahafi.model.Categoria;
import com.sfahafi.repository.I_CategoriasRepository;
import com.sfahafi.service.I_CategoriasService;

@Service
@Primary
public class CategoriasServiceJpa implements I_CategoriasService {

	@Autowired
	private I_CategoriasRepository cr;
	
	@Override
	public void guardar(Categoria categoria) {
		cr.save(categoria);

	}

	@Override
	public List<Categoria> buscarTodas() {
		return cr.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> optional = cr.findById(idCategoria);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		cr.deleteById(idCategoria);
		
	}

	@Override
	public Page<Categoria> buscartodas(Pageable page) {
		return cr.findAll(page);
	}

}
