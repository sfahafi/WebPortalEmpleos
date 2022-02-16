package com.sfahafi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sfahafi.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
