package com.jlcb.desafioprodutecbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jlcb.desafioprodutecbackend.model.enums.Perfil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "senha")
	@JsonIgnore
	private String senha;
	
	@Column(name = "perfil")
	@Enumerated(EnumType.STRING) 
	private Perfil perfil;
	
	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;
}
