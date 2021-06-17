package com.jlcb.desafioprodutecbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Representante extends Usuario {
	
	@Column(name = "nome_fantasia")
	private String nomeFantasia;

	@Column(name = "telefone")
	private String telefone;
}
