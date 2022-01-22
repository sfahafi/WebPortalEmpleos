package com.sfahafi.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sfahafi.model.Vacante;
import com.sfahafi.repository.I_VacantesRepository;
import com.sfahafi.service.I_VacantesService;

@Service
@Primary
public class VacantesServiceJpa implements I_VacantesService {
	
	@Autowired
	private I_VacantesRepository vr;

	@Override
	public List<Vacante> buscarTodas() {
		return vr.findAll();
	}

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		Optional<Vacante> optional = vr.findById(idVacante);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		vr.save(vacante);

	}

	@Override
	public List<Vacante> buscarDestacadas() {
		return vr.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

}
