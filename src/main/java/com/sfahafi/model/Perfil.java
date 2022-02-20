package com.sfahafi.model;

import javax.persistence.*;

@Entity
@Table(name="Perfiles")
public class Perfil {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String perfil;

	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "UsuarioPerfil", // tabla intermedia
			joinColumns = @JoinColumn(name = "idPerfil"), 
			inverseJoinColumns = @JoinColumn(name = "idUsuario") 
	)
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "Perfil [id=" + id + ", perfil=" + perfil + "]";
	}
	
	
}
