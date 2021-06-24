package com.jlcb.desafioprodutecbackend.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import com.jlcb.desafioprodutecbackend.model.enums.Perfil;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Gerente extends Usuario {
	
	public Gerente() {

	}
	
	public Gerente(Long id, String nome, String email, String senha, Perfil perfil) {
		setId(id);
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setPerfil(perfil);
	}
}
