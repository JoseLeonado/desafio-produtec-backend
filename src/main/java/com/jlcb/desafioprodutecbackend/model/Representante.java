package com.jlcb.desafioprodutecbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Representante extends Empresa {

	@Column(name = "telefone")
	private String telefone;
}
