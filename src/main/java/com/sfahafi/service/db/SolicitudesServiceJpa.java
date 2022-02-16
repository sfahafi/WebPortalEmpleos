package com.sfahafi.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sfahafi.model.Solicitud;
import com.sfahafi.repository.SolicitudesRepository;
import com.sfahafi.service.I_SolicitudesService;

@Service
public class SolicitudesServiceJpa implements I_SolicitudesService {

	@Autowired
	private SolicitudesRepository sr;
	
	@Override
	public void guardar(Solicitud solicitud) {
		sr.save(solicitud);
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		sr.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return sr.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> optional = sr.findById(idSolicitud);
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		return sr.findAll(page);
	}

}
