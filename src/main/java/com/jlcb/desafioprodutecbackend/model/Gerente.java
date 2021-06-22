package com.jlcb.desafioprodutecbackend.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Gerente extends Usuario {
	
}
