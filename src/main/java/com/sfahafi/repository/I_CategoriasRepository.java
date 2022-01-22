package com.sfahafi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;

import com.sfahafi.model.Categoria;

// public interface I_CategoriasRepository extends CrudRepository<Categoria, Integer> {

public interface I_CategoriasRepository extends JpaRepository<Categoria, Integer> {


}
