package com.sfahafi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sfahafi.model.Vacante;

public interface I_VacantesRepository extends JpaRepository<Vacante, Integer> {

	// Query Methods Definicion del metodo
	
	List<Vacante> findByEstatus(String estatus);
	
	List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus);
	
	List<Vacante> findBySalarioBetweenOrderBySalarioDesc(Double s1, Double s2);
	
	List<Vacante> findByEstatusIn(String[] estatus);
	
}
