package com.jlcb.desafioprodutecbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Representante extends Usuario {
	
	@Column(name = "nome_fantasia")
	private String nomeFantasia;

	@Column(name = "telefone")
	private String telefone;
}
