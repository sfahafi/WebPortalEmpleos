package com.sfahafi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sfahafi.model.Usuario;

public interface I_UsuariosRepository extends JpaRepository<Usuario, Integer> {

}
